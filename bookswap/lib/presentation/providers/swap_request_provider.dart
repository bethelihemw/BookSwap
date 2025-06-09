import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:bookswap/domain/entities/resource.dart';
import 'package:bookswap/data/repositories/resource_repository_impl.dart';
import 'package:bookswap/data/services/api_service.dart';

final swapRequestRepositoryProvider = Provider<ResourceRepositoryImpl>((ref) {
  return ResourceRepositoryImpl(ApiService());
});

final swapRequestProvider = StateNotifierProvider<SwapRequestNotifier, List<SwapRequest>>((ref) {
  return SwapRequestNotifier(ref.watch(swapRequestRepositoryProvider));
});

class SwapRequestNotifier extends StateNotifier<List<SwapRequest>> {
  final ResourceRepositoryImpl repository;

  SwapRequestNotifier(this.repository) : super([]);

  Future<void> fetchSwapRequests(String userId, {String? token}) async {
    state = await repository.getSwapRequests(userId, token: token);
  }

  Future<void> addSwapRequest(SwapRequest? swapRequest, {String? token}) async {
    if (swapRequest != null) {
      final newRequest = await repository.addSwapRequest(swapRequest, token: token);
      state = [...state, newRequest];
    }
  }

  Future<void> updateSwapRequest(SwapRequest? swapRequest, {String? token}) async {
    if (swapRequest != null) {
      final updatedRequest = await repository.updateSwapRequest(swapRequest, token: token);
      state = state.map((r) => r.id == updatedRequest.id ? updatedRequest : r).toList();
    }
  }

  Future<void> deleteSwapRequest(String? id, {String? token}) async {
    if (id != null) {
      await repository.deleteSwapRequest(id, token: token);
      state = state.where((r) => r.id != id).toList();
    }
  }

  Future<void> completeSwapRequest(String? id, {String? token}) async {
    if (id != null) {
      final completedRequest = await repository.completeSwapRequest(id, token: token);
      state = state.map((r) => r.id == completedRequest.id ? completedRequest : r).toList();
    }
  }
}