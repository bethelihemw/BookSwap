import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:bookswap/domain/entities/resource.dart';

class ResourceNotifier extends StateNotifier<List<Resource>> {
  ResourceNotifier() : super([]);

  void addResource(Resource resource) {
    state = [...state, resource];
  }
}

final resourceProvider = StateNotifierProvider<ResourceNotifier, List<Resource>>((ref) {
  return ResourceNotifier();
});