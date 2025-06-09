import 'package:flutter_test/flutter_test.dart';
import 'package:integration_test/integration_test.dart';
import 'package:bookswap/main.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart'; // Add this import

void main() {
  IntegrationTestWidgetsFlutterBinding.ensureInitialized();

  testWidgets("Add resource and verify it appears in My Resources", (WidgetTester tester) async {
    await tester.pumpWidget(const ProviderScope(child: MyApp()));
    // Add navigation and verification logic here
  });
}