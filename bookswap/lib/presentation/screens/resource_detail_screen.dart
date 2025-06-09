import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:bookswap/domain/entities/resource.dart';
import 'package:bookswap/presentation/providers/auth_provider.dart';
import 'package:bookswap/presentation/providers/swap_request_provider.dart';
import 'package:uuid/uuid.dart';

class ResourceDetailScreen extends ConsumerWidget {
  final Resource resource;

  const ResourceDetailScreen({super.key, required this.resource});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final token = ref.watch(authProvider).token;
    final email = ref.watch(authProvider).email;

    if (token == null || email == null) {
      return const Scaffold(
        body: Center(child: Text('Not authenticated')),
      );
    }

    return Scaffold(
      appBar: AppBar(
        title: Text(resource.title),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Center(
              child: resource.coverImage != null
                  ? Image.network(
                'http://localhost:3000${resource.coverImage}',
                height: 280,
                width: 190,
                fit: BoxFit.cover,
                errorBuilder: (context, error, stackTrace) => Image.asset(
                  'assets/default_image.png',
                  height: 280,
                  width: 190,
                  fit: BoxFit.cover,
                ),
              )
                  : Image.asset(
                'assets/default_image.png',
                height: 280,
                width: 190,
                fit: BoxFit.cover,
              ),
            ),
            const SizedBox(height: 16),
            Text(
              resource.title,
              style: const TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 8),
            Text('Author: ${resource.author ?? 'Unknown'}'),
            Text('Language: ${resource.language ?? 'Unknown'}'),
            Text('Edition: ${resource.edition ?? 'N/A'}'),
            Text('Genre: ${resource.genre ?? 'Unknown'}'),
            Text('Owner: ${resource.owner}'),
            const SizedBox(height: 16),
            Text(resource.description),
            const Spacer(),
            Center(
              child: ElevatedButton(
                onPressed: () => _showTradeDialog(context, ref, resource, email, token),
                style: ElevatedButton.styleFrom(
                  minimumSize: const Size(double.infinity, 50),
                  backgroundColor: Colors.purple,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
                child: const Text(
                  "Request Trade",
                  style: TextStyle(fontSize: 18, color: Colors.white),
                ),
              ),
            ),
          ],
        ),
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