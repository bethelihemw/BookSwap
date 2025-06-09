import 'package:bookswap/data/models/resource_model.dart';
import 'package:bookswap/data/models/swap_request_model.dart';
import 'package:bookswap/data/services/api_service.dart';
import 'package:bookswap/domain/entities/resource.dart';
import 'package:bookswap/domain/repositories/resource_repository.dart';

class ResourceRepositoryImpl implements ResourceRepository {
  final ApiService apiService;

  ResourceRepositoryImpl(this.apiService);

  @override
  Future<List<Resource>> getResources() async {
    final resourceModels = await apiService.getResources();
    return resourceModels
        .map((model) => Resource(
      id: model.id,
      title: model.title,
      owner: model.owner,
      description: model.description,
      coverImage: model.coverImage,
      author: model.author,
      language: model.language,
      edition: model.edition,
      genre: model.genre,
    ))
        .toList();
  }

  @override
  Future<Resource> addResource(Resource resource, {String? token, List<int>? imageBytes, String? imageName}) async {
    final resourceModel = ResourceModel(
      id: resource.id,
      title: resource.title,
      owner: resource.owner,
      description: resource.description,
      coverImage: resource.coverImage,
      author: resource.author ?? '',
      language: resource.language ?? '',
      edition: resource.edition ?? '',
      genre: resource.genre ?? '',
    );
    final newResourceModel = await apiService.addResource(resourceModel, token: token, imageBytes: imageBytes, imageName: imageName);
    return Resource(
      id: newResourceModel.id,
      title: newResourceModel.title,
      owner: newResourceModel.owner,
      description: newResourceModel.description,
      coverImage: newResourceModel.coverImage,
      author: newResourceModel.author,
      language: newResourceModel.language,
      edition: newResourceModel.edition,
      genre: newResourceModel.genre,
    );
  }

  @override
  Future<Resource> updateResource(Resource resource, {String? token}) async {
    final resourceModel = ResourceModel(
      id: resource.id,
      title: resource.title,
      owner: resource.owner,
      description: resource.description,
      coverImage: resource.coverImage,
      author: resource.author ?? '',
      language: resource.language ?? '',
      edition: resource.edition ?? '',
      genre: resource.genre ?? '',
    );
    final updatedResourceModel = await apiService.updateResource(resourceModel, token: token);
    return Resource(
      id: updatedResourceModel.id,
      title: updatedResourceModel.title,
      owner: updatedResourceModel.owner,
      description: updatedResourceModel.description,
      coverImage: updatedResourceModel.coverImage,
      author: updatedResourceModel.author,
      language: updatedResourceModel.language,
      edition: updatedResourceModel.edition,
      genre: updatedResourceModel.genre,
    );
  }

  @override
  Future<void> deleteResource(String id, {String? token}) async {
    await apiService.deleteResource(id, token: token);
  }

  @override
  Future<List<SwapRequest>> getSwapRequests(String userId, {String? token}) async {
    final swapRequestModels = await apiService.getSwapRequests(userId, token: token);
    return swapRequestModels
        .map((model) => SwapRequest(
      id: model.id,
      offeredBookId: model.offeredBookId,
      requestedBookId: model.requestedBookId,
      requester: model.requester,
      owner: model.owner,
      status: model.status,
      notesFromRequester: model.notesFromRequester,
      notesFromOwner: model.notesFromOwner,
      proposedBookId: model.proposedBookId,
      counterAcceptedByRequester: model.counterAcceptedByRequester,
    ))
        .toList();
  }

  @override
  Future<SwapRequest> addSwapRequest(SwapRequest swapRequest, {String? token}) async {
    final swapRequestModel = SwapRequestModel(
      id: swapRequest.id,
      offeredBookId: swapRequest.offeredBookId,
      requestedBookId: swapRequest.requestedBookId,
      requester: swapRequest.requester,
      owner: swapRequest.owner,
      status: swapRequest.status,
      notesFromRequester: swapRequest.notesFromRequester,
      notesFromOwner: swapRequest.notesFromOwner,
      proposedBookId: swapRequest.proposedBookId,
      counterAcceptedByRequester: swapRequest.counterAcceptedByRequester,
    );
    final newSwapRequestModel = await apiService.addSwapRequest(swapRequestModel, token: token);
    return SwapRequest(
      id: newSwapRequestModel.id,
      offeredBookId: newSwapRequestModel.offeredBookId,
      requestedBookId: newSwapRequestModel.requestedBookId,
      requester: newSwapRequestModel.requester,
      owner: newSwapRequestModel.owner,
      status: newSwapRequestModel.status,
      notesFromRequester: newSwapRequestModel.notesFromRequester,
      notesFromOwner: newSwapRequestModel.notesFromOwner,
      proposedBookId: newSwapRequestModel.proposedBookId,
      counterAcceptedByRequester: newSwapRequestModel.counterAcceptedByRequester,
    );
  }

  @override
  Future<SwapRequest> updateSwapRequest(SwapRequest swapRequest, {String? token}) async {
    final updatedSwapRequestModel = await apiService.updateSwapRequest(
      swapRequest.id,
      status: swapRequest.status,
      proposedBookId: swapRequest.proposedBookId,
      notes: swapRequest.notesFromOwner,
      token: token,
    );
    return SwapRequest(
      id: updatedSwapRequestModel.id,
      offeredBookId: updatedSwapRequestModel.offeredBookId,
      requestedBookId: updatedSwapRequestModel.requestedBookId,
      requester: updatedSwapRequestModel.requester,
      owner: updatedSwapRequestModel.owner,
      status: updatedSwapRequestModel.status,
      notesFromRequester: updatedSwapRequestModel.notesFromRequester,
      notesFromOwner: updatedSwapRequestModel.notesFromOwner,
      proposedBookId: updatedSwapRequestModel.proposedBookId,
      counterAcceptedByRequester: updatedSwapRequestModel.counterAcceptedByRequester,
    );
  }

  @override
  Future<void> deleteSwapRequest(String id, {String? token}) async {
    await apiService.deleteSwapRequest(id, token: token);
  }

  @override
  Future<SwapRequest> completeSwapRequest(String id, {String? token}) async {
    final completedSwapRequestModel = await apiService.completeSwapRequest(id, token: token);
    return SwapRequest(
      id: completedSwapRequestModel.id,
      offeredBookId: completedSwapRequestModel.offeredBookId,
      requestedBookId: completedSwapRequestModel.requestedBookId,
      requester: completedSwapRequestModel.requester,
      owner: completedSwapRequestModel.owner,
      status: completedSwapRequestModel.status,
      notesFromRequester: completedSwapRequestModel.notesFromRequester,
      notesFromOwner: completedSwapRequestModel.notesFromOwner,
      proposedBookId: completedSwapRequestModel.proposedBookId,
      counterAcceptedByRequester: completedSwapRequestModel.counterAcceptedByRequester,
    );
  }
}