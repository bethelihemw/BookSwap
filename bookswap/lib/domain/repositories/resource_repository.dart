import 'package:bookswap/domain/entities/resource.dart';

abstract class ResourceRepository {
  Future<List<Resource>> getResources();
  Future<Resource> addResource(Resource resource);
  Future<Resource> updateResource(Resource resource);
  Future<void> deleteResource(String id);

  Future<List<SwapRequest>> getSwapRequests(String userId);
  Future<SwapRequest> addSwapRequest(SwapRequest swapRequest);
  Future<SwapRequest> updateSwapRequest(SwapRequest swapRequest);
  Future<void> deleteSwapRequest(String id);
}