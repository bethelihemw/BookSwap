import "package:bookswap/domain/entities/resource.dart";
import "package:bookswap/domain/repositories/resource_repository.dart";

class GetResourcesUseCase {
  final ResourceRepository repository;

  GetResourcesUseCase(this.repository);

  Future<List<Resource>> execute() async {
    return await repository.getResources();
  }
}
