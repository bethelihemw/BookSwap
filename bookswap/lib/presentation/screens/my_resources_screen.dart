import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:bookswap/domain/entities/resource.dart';
import 'package:bookswap/presentation/providers/auth_provider.dart';
import 'package:bookswap/presentation/providers/swap_request_provider.dart';

class MyResourcesScreen extends ConsumerWidget {
  const MyResourcesScreen({super.key});

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
        title: const Text("My Books"),
        backgroundColor: const Color(0xFF9C27B0),
      ),
      body: FutureBuilder<List<Resource>>(
        future: repository.getResources(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          }

          final resources = snapshot.data?.where((r) => r.owner == email).toList() ?? [];
          if (resources.isEmpty) {
            return const Center(child: Text('You have no books. Add one!'));
          }

          return ListView.builder(
            padding: const EdgeInsets.all(16.0),
            itemCount: resources.length,
            itemBuilder: (context, index) {
              final resource = resources[index];
              return Card(
                elevation: 2,
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
                  subtitle: Text('Author: ${resource.author ?? 'Unknown'}'),
                  trailing: Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      IconButton(
                        icon: const Icon(Icons.edit, color: Colors.blue),
                        onPressed: () => _showEditDialog(context, ref, resource, token),
                      ),
                      IconButton(
                        icon: const Icon(Icons.delete, color: Colors.red),
                        onPressed: () => _deleteBook(context, ref, resource.id, token),
                      ),
                    ],
                  ),
                ),
              );
            },
          );
        },
      ),
    );
  }

  Future<void> _showEditDialog(BuildContext context, WidgetRef ref, Resource resource, String token) async {
    final titleController = TextEditingController(text: resource.title);
    final authorController = TextEditingController(text: resource.author);
    final descriptionController = TextEditingController(text: resource.description);

    await showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Edit Book'),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            TextField(
              controller: titleController,
              decoration: const InputDecoration(labelText: 'Title'),
            ),
            TextField(
              controller: authorController,
              decoration: const InputDecoration(labelText: 'Author'),
            ),
            TextField(
              controller: descriptionController,
              decoration: const InputDecoration(labelText: 'Description'),
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
              try {
                final updatedResource = Resource(
                  id: resource.id,
                  title: titleController.text,
                  author: authorController.text,
                  language: resource.language,
                  edition: resource.edition,
                  description: descriptionController.text,
                  genre: resource.genre,
                  owner: resource.owner,
                  coverImage: resource.coverImage,
                );
                await ref.read(swapRequestRepositoryProvider).updateResource(updatedResource, token: token);
                Navigator.pop(context);
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text('Book updated successfully')),
                );
              } catch (e) {
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(content: Text('Error: $e')),
                );
              }
            },
            child: const Text('Save'),
          ),
        ],
      ),
    );
  }

  Future<void> _deleteBook(BuildContext context, WidgetRef ref, String id, String token) async {
    try {
      await ref.read(swapRequestRepositoryProvider).deleteResource(id, token: token);
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Book deleted successfully')),
      );
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: $e')),
      );
    }
  }
}