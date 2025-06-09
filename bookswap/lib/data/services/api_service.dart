import 'package:dio/dio.dart';
import 'package:bookswap/data/models/resource_model.dart';
import 'package:bookswap/data/models/swap_request_model.dart';

class ApiService {
  final Dio dio;

  ApiService()
      : dio = Dio(BaseOptions(
    baseUrl: 'http://10.4.116.149:3000/api/v1',
    connectTimeout: const Duration(seconds: 5),
    receiveTimeout: const Duration(seconds: 3),
  ));

  Future<List<ResourceModel>> getResources({String? token}) async {
    try {
      print('ApiService - Sending GET request to /resources with token: $token');
      final response = await dio.get(
        '/resources',
        options: Options(
          headers: {
            'Authorization': 'Bearer $token',
            'Content-Type': 'application/json',
          },
        ),
      );
      print('ApiService - Response status: ${response.statusCode}');
      print('ApiService - Response data: ${response.data}');
      return (response.data['data'] as List<dynamic>)
          .map((json) => ResourceModel.fromJson(json))
          .toList();
    } catch (e) {
      print('ApiService - Error fetching resources: $e');
      rethrow;
    }
  }

  Future<ResourceModel> addResource(
      ResourceModel resource, {
        String? token,
        List<int>? imageBytes,
        String? imageName,
      }) async {
    try {
      final formData = FormData.fromMap(resource.toJson()..removeWhere((key, value) => value == null));
      if (imageBytes != null && imageName != null) {
        formData.files.add(MapEntry('coverImage', MultipartFile.fromBytes(imageBytes, filename: imageName)));
      }
      print('ApiService - Sending POST request to /resources with token: $token');
      final response = await dio.post(
        '/resources',
        data: formData,
        options: Options(headers: {'Authorization': 'Bearer $token'}),
      );
      print('ApiService - Response status: ${response.statusCode}');
      return ResourceModel.fromJson(response.data['data']);
    } catch (e) {
      print('ApiService - Error adding resource: $e');
      rethrow;
    }
  }

  Future<ResourceModel> updateResource(ResourceModel resource, {String? token}) async {
    try {
      print('ApiService - Sending PUT request to /resources/${resource.id} with token: $token');
      final response = await dio.put(
        '/resources/${resource.id}',
        data: resource.toJson()..removeWhere((key, value) => value == null),
        options: Options(headers: {'Authorization': 'Bearer $token'}),
      );
      print('ApiService - Response status: ${response.statusCode}');
      return ResourceModel.fromJson(response.data['data']);
    } catch (e) {
      print('ApiService - Error updating resource: $e');
      rethrow;
    }
  }

  Future<void> deleteResource(String id, {String? token}) async {
    try {
      print('ApiService - Sending DELETE request to /resources/$id with token: $token');
      await dio.delete(
        '/resources/$id',
        options: Options(headers: {'Authorization': 'Bearer $token'}),
      );
      print('ApiService - Resource deleted successfully');
    } catch (e) {
      print('ApiService - Error deleting resource: $e');
      rethrow;
    }
  }

  Future<List<SwapRequestModel>> getSwapRequests(String userId, {String? token}) async {
    try {
      print('ApiService - Sending GET request to /trades with token: $token');
      final response = await dio.get(
        '/trades',
        queryParameters: {'userId': userId},
        options: Options(headers: {'Authorization': 'Bearer $token'}),
      );
      print('ApiService - Response status: ${response.statusCode}');
      return (response.data['data'] as List<dynamic>)
          .map((json) => SwapRequestModel.fromJson(json))
          .toList();
    } catch (e) {
      print('ApiService - Error fetching swap requests: $e');
      rethrow;
    }
  }

  Future<SwapRequestModel> addSwapRequest(SwapRequestModel swapRequest, {String? token}) async {
    try {
      print('ApiService - Sending POST request to /trades with token: $token');
      final response = await dio.post(
        '/trades',
        data: {
          'offeredBookId': swapRequest.offeredBookId,
          'requestedBookId': swapRequest.requestedBookId,
          'notes': swapRequest.notesFromRequester,
        }..removeWhere((key, value) => value == null),
        options: Options(headers: {'Authorization': 'Bearer $token'}),
      );
      print('ApiService - Response status: ${response.statusCode}');
      return SwapRequestModel.fromJson(response.data['data']);
    } catch (e) {
      print('ApiService - Error adding swap request: $e');
      rethrow;
    }
  }

  Future<SwapRequestModel> updateSwapRequest(
      String id, {
        String? status,
        String? proposedBookId,
        String? notes,
        String? token,
      }) async {
    try {
      print('ApiService - Sending PUT request to /trades/$id with token: $token');
      final response = await dio.put(
        '/trades/$id',
        data: {
          'status': status,
          'proposedBookId': proposedBookId,
          'notes': notes,
        }..removeWhere((key, value) => value == null),
        options: Options(headers: {'Authorization': 'Bearer $token'}),
      );
      print('ApiService - Response status: ${response.statusCode}');
      return SwapRequestModel.fromJson(response.data['data']);
    } catch (e) {
      print('ApiService - Error updating swap request: $e');
      rethrow;
    }
  }

  Future<void> deleteSwapRequest(String id, {String? token}) async {
    try {
      print('ApiService - Sending DELETE request to /trades/$id with token: $token');
      await dio.delete(
        '/trades/$id',
        options: Options(headers: {'Authorization': 'Bearer $token'}),
      );
      print('ApiService - Swap request deleted successfully');
    } catch (e) {
      print('ApiService - Error deleting swap request: $e');
      rethrow;
    }
  }

  Future<SwapRequestModel> completeSwapRequest(String id, {String? token}) async {
    try {
      print('ApiService - Sending PUT request to /trades/$id/complete with token: $token');
      final response = await dio.put(
        '/trades/$id/complete',
        options: Options(headers: {'Authorization': 'Bearer $token'}),
      );
      print('ApiService - Response status: ${response.statusCode}');
      return SwapRequestModel.fromJson(response.data['data']);
    } catch (e) {
      print('ApiService - Error completing swap request: $e');
      rethrow;
    }
  }
}