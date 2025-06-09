import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:bookswap/domain/entities/resource.dart';
import 'package:bookswap/presentation/providers/auth_provider.dart';
import 'package:bookswap/presentation/providers/swap_request_provider.dart';
import 'package:uuid/uuid.dart';

class HomeScreen extends ConsumerWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final token = ref.watch(authProvider).token;
    final email = ref.watch(authProvider).email;
    final repository = ref.watch(swapRequestRepositoryProvider);

    if (token == null || email == null) {
      return const Scaffold(
        body: Center(child: Text('Not authenticated')),
      );
    }

    return Scaffold(
      appBar: AppBar(
        title: const Text("BookSwap"),
      ),
      body: FutureBuilder<List<Resource>>(
        future: repository.getResources(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text('No books available.'));
          }

          final resources = snapshot.data!;
          return ListView.builder(
            padding: const EdgeInsets.all(16.0),
            itemCount: resources.length,
            itemBuilder: (context, index) {
              final resource = resources[index];
              return Card(
                child: ListTile(
                  leading: resource.coverImage != null
                      ? Image.network(
                    'http://localhost:3000${resource.coverImage}',
                    width: 50,
                    height: 50,
                    fit: BoxFit.cover,
                    errorBuilder: (context, error, stackTrace) => const Icon(Icons.book, size: 50),
                  )
                      : const Icon(Icons.book, size: 50),
                  title: Text(resource.title),
                  subtitle: Text('Author: ${resource.author}\nOwner: ${resource.owner}'),
                  trailing: ElevatedButton(
                    onPressed: () => _showTradeDialog(context, ref, resource, email, token),
                    child: const Text("Request Trade"),
                  ),
                ),
              );
            },
          );
        },
      ),
    );
  }

  Future<void> _showTradeDialog(BuildContext context, WidgetRef ref, Resource requestedBook, String email, String token) async {
    Resource? selectedOfferedBook;
    final notesController = TextEditingController();

    final userBooks = (await ref.read(swapRequestRepositoryProvider).getResources())
        .where((book) => book.owner == email)
        .toList();

    if (userBooks.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('You need to add a book to offer for trade')),
      );
      return;
    }

    await showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Initiate Trade'),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            const Text('Select a book to offer:'),
            DropdownButton<Resource>(
              value: selectedOfferedBook,
              hint: const Text('Choose a book'),
              isExpanded: true,
              items: userBooks
                  .map((book) => DropdownMenuItem(
                value: book,
                child: Text(book.title),
              ))
                  .toList(),
              onChanged: (value) {
                selectedOfferedBook = value;
              },
            ),
            TextField(
              controller: notesController,
              decoration: const InputDecoration(labelText: 'Notes (optional)'),
            ),
          ],
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('Cancel'),
          ),
          ElevatedButton(
            onPressed: () async {
              if (selectedOfferedBook == null) {
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text('Please select a book to offer')),
                );
                return;
              }
              try {
                final swapRequest = SwapRequest(
                  id: const Uuid().v4(),
                  offeredBookId: selectedOfferedBook!.id,
                  requestedBookId: requestedBook.id,
                  requester: email,
                  owner: requestedBook.owner,
                  status: 'proposed',
                  notesFromRequester: notesController.text.isNotEmpty ? notesController.text : null,
                );
                await ref.read(swapRequestProvider.notifier).addSwapRequest(swapRequest, token: token);
                Navigator.pop(context);
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text('Trade requested successfully')),
                );
              } catch (e) {
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(content: Text('Error: $e')),
                );
              }
            },
            child: const Text('Submit'),
          ),
        ],
      ),
    );
  }
}