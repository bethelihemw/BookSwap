import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:bookswap/domain/entities/resource.dart';
import 'package:bookswap/presentation/providers/auth_provider.dart';
import 'package:bookswap/presentation/providers/swap_request_provider.dart';

class SwapRequestsScreen extends ConsumerWidget {
  const SwapRequestsScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final token = ref.watch(authProvider).token;
    final email = ref.watch(authProvider).email;
    final swapRequests = ref.watch(swapRequestProvider);

    if (token == null || email == null) {
      return const Scaffold(
        body: Center(child: Text('Not authenticated')),
      );
    }

    return Scaffold(
      appBar: AppBar(
        title: const Text("My Trades"),
      ),
      body: RefreshIndicator(
        onRefresh: () => ref.read(swapRequestProvider.notifier).fetchSwapRequests(email, token: token),
        child: FutureBuilder(
          future: ref.read(swapRequestProvider.notifier).fetchSwapRequests(email, token: token),
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.waiting) {
              return const Center(child: CircularProgressIndicator());
            } else if (snapshot.hasError) {
              return Center(child: Text('Error: ${snapshot.error}'));
            }

            if (swapRequests.isEmpty) {
              return const Center(child: Text('No trades found.'));
            }

            return ListView.builder(
              padding: const EdgeInsets.all(16.0),
              itemCount: swapRequests.length,
              itemBuilder: (context, index) {
                final swapRequest = swapRequests[index];
                return Card(
                  child: ExpansionTile(
                    title: Text('Trade for Book ID: ${swapRequest.requestedBookId}'),
                    subtitle: Text('Status: ${swapRequest.status}\nFrom: ${swapRequest.requester}'),
                    children: [
                      ListTile(
                        title: const Text('Offered Book'),
                        subtitle: Text('Book ID: ${swapRequest.offeredBookId}'),
                      ),
                      if (swapRequest.notesFromRequester != null)
                        ListTile(
                          title: const Text('Notes from Requester'),
                          subtitle: Text(swapRequest.notesFromRequester!),
                        ),
                      if (swapRequest.proposedBookId != null)
                        ListTile(
                          title: const Text('Proposed Book by Owner'),
                          subtitle: Text('Book ID: ${swapRequest.proposedBookId!}'),
                        ),
                      if (swapRequest.notesFromOwner != null)
                        ListTile(
                          title: const Text('Notes from Owner'),
                          subtitle: Text(swapRequest.notesFromOwner!),
                        ),
                      if (email == swapRequest.owner && swapRequest.status == 'proposed')
                        Row(
                          mainAxisAlignment: MainAxisAlignment.end,
                          children: [
                            IconButton(
                              icon: const Icon(Icons.check, color: Colors.green),
                              onPressed: () => _respondToTrade(context, ref, swapRequest, 'accepted', token),
                              tooltip: 'Accept Trade',
                            ),
                            IconButton(
                              icon: const Icon(Icons.swap_horiz, color: Colors.blue),
                              onPressed: () => _proposeBook(context, ref, swapRequest, email, token),
                              tooltip: 'Propose Another Book',
                            ),
                            IconButton(
                              icon: const Icon(Icons.close, color: Colors.red),
                              onPressed: () => _respondToTrade(context, ref, swapRequest, 'rejected', token),
                              tooltip: 'Reject Trade',
                            ),
                          ],
                        ),
                      if (email == swapRequest.requester && swapRequest.proposedBookId != null && !swapRequest.counterAcceptedByRequester!)
                        IconButton(
                          icon: const Icon(Icons.check_circle, color: Colors.green),
                          onPressed: () => _counterAccept(context, ref, swapRequest, token),
                          tooltip: 'Accept Proposed Book',
                        ),
                      if ((swapRequest.status == 'accepted' || (swapRequest.proposedBookId != null && swapRequest.counterAcceptedByRequester!)) &&
                          ['accepted', 'proposed'].contains(swapRequest.status))
                        IconButton(
                          icon: const Icon(Icons.done_all, color: Colors.purple),
                          onPressed: () => _completeTrade(context, ref, swapRequest, token),
                          tooltip: 'Complete Trade',
                        ),
                      IconButton(
                        icon: const Icon(Icons.delete, color: Colors.red),
                        onPressed: () async {
                          try {
                            await ref.read(swapRequestProvider.notifier).deleteSwapRequest(swapRequest.id, token: token);
                            ScaffoldMessenger.of(context).showSnackBar(
                              const SnackBar(content: Text('Trade cancelled')),
                            );
                          } catch (e) {
                            ScaffoldMessenger.of(context).showSnackBar(
                              SnackBar(content: Text('Error: $e')),
                            );
                          }
                        },
                        tooltip: 'Cancel Trade',
                      ),
                    ],
                  ),
                );
              },
            );
          },
        ),
      ),
    );
  }

  Future<void> _respondToTrade(BuildContext context, WidgetRef ref, SwapRequest swapRequest, String status, String token) async {
    try {
      await ref.read(swapRequestProvider.notifier).updateSwapRequest(
        SwapRequest(
          id: swapRequest.id,
          offeredBookId: swapRequest.offeredBookId,
          requestedBookId: swapRequest.requestedBookId,
          requester: swapRequest.requester,
          owner: swapRequest.owner,
          status: status,
          notesFromRequester: swapRequest.notesFromRequester,
          notesFromOwner: swapRequest.notesFromOwner,
          proposedBookId: swapRequest.proposedBookId,
          counterAcceptedByRequester: swapRequest.counterAcceptedByRequester,
        ),
        token: token,
      );
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Trade $status')),
      );
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: $e')),
      );
    }
  }

  Future<void> _proposeBook(BuildContext context, WidgetRef ref, SwapRequest swapRequest, String email, String token) async {
    Resource? selectedProposedBook;
    final notesController = TextEditingController();

    final userBooks = (await ref.read(swapRequestRepositoryProvider).getResources())
        .where((book) => book.owner == email)
        .toList();

    await showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Propose Another Book'),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            const Text('Select a book to propose:'),
            DropdownButton<Resource>(
              value: selectedProposedBook,
              hint: const Text('Choose a book'),
              isExpanded: true,
              items: userBooks
                  .map((book) => DropdownMenuItem(
                value: book,
                child: Text(book.title),
              ))
                  .toList(),
              onChanged: (value) {
                selectedProposedBook = value;
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
              if (selectedProposedBook == null) {
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text('Please select a book to propose')),
                );
                return;
              }
              try {
                await ref.read(swapRequestProvider.notifier).updateSwapRequest(
                  SwapRequest(
                    id: swapRequest.id,
                    offeredBookId: swapRequest.offeredBookId,
                    requestedBookId: swapRequest.requestedBookId,
                    requester: swapRequest.requester,
                    owner: swapRequest.owner,
                    status: 'proposed',
                    notesFromRequester: swapRequest.notesFromRequester,
                    notesFromOwner: notesController.text.isNotEmpty ? notesController.text : null,
                    proposedBookId: selectedProposedBook!.id,
                    counterAcceptedByRequester: swapRequest.counterAcceptedByRequester,
                  ),
                  token: token,
                );
                Navigator.pop(context);
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text('Book proposed successfully')),
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

  Future<void> _counterAccept(BuildContext context, WidgetRef ref, SwapRequest swapRequest, String token) async {
    try {
      await ref.read(swapRequestProvider.notifier).completeSwapRequest(swapRequest.id, token: token);
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Proposed book accepted')),
      );
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: $e')),
      );
    }
  }

  Future<void> _completeTrade(BuildContext context, WidgetRef ref, SwapRequest swapRequest, String token) async {
    try {
      await ref.read(swapRequestProvider.notifier).completeSwapRequest(swapRequest.id, token: token);
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Trade completed successfully')),
      );
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: $e')),
      );
    }
  }
}