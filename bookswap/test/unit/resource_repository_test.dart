import 'package:bookswap/data/repositories/resource_repository_impl.dart';
import 'package:bookswap/data/services/api_service.dart';
import 'package:bookswap/data/models/resource_model.dart';
import 'package:mockito/annotations.dart';
import 'package:mockito/mockito.dart'; // Add this for when, verify
import 'package:test/test.dart'; // Correct import for test framework
import 'resource_repository_test.mocks.dart';

@GenerateMocks([ApiService])
void main() {
  late ResourceRepositoryImpl repository;
  late MockApiService mockApiService;

  setUp(() {
    mockApiService = MockApiService();
    repository = ResourceRepositoryImpl(mockApiService);
  });

  group('ResourceRepositoryImpl Tests', () {
    test("getResources returns a list of resources", () async {
      // Arrange
      final resources = [
        ResourceModel(
          id: "1",
          title: "Tool",
          owner: "John",
          description: "Hammer",
        ),
      ];
      when(mockApiService.getResources()).thenAnswer((_) async => resources);

      // Act
      final result = await repository.getResources();

      // Assert
      expect(result, equals(resources));
      verify(mockApiService.getResources()).called(1);
    });
  });
}