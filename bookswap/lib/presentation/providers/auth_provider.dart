import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class AuthState {
  final String? token;
  final String? email;

  const AuthState({this.token, this.email});

  AuthState copyWith({String? token, String? email}) {
    return AuthState(
      token: token ?? this.token,
      email: email ?? this.email,
    );
  }
}

class AuthNotifier extends StateNotifier<AuthState> {
  final FlutterSecureStorage _storage;

  AuthNotifier() : _storage = const FlutterSecureStorage(), super(const AuthState()) {
    _loadAuthState();
  }

  Future<void> _loadAuthState() async {
    final token = await _storage.read(key: 'auth_token');
    final email = await _storage.read(key: 'email');
    if (token != null && email != null) {
      state = AuthState(token: token, email: email);
    }
  }

  Future<void> setAuthenticated(String token, String email) async {
    await _storage.write(key: 'auth_token', value: token);
    await _storage.write(key: 'email', value: email);
    state = AuthState(token: token, email: email);
  }

  Future<void> logout() async {
    await _storage.delete(key: 'auth_token');
    await _storage.delete(key: 'email');
    state = const AuthState();
  }

  Future<void> deleteAccount() async {
    await _storage.deleteAll();
    state = const AuthState();
  }
}

final authProvider = StateNotifierProvider<AuthNotifier, AuthState>((ref) => AuthNotifier());