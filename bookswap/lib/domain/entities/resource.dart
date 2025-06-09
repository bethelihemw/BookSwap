import 'package:equatable/equatable.dart';

class Resource extends Equatable {
  final String id;
  final String title;
  final String owner;
  final String description;
  final String? coverImage;
  final String? author;
  final String? language;
  final String? edition;
  final String? genre;

  const Resource({
    required this.id,
    required this.title,
    required this.owner,
    required this.description,
    this.coverImage,
    this.author,
    this.language,
    this.edition,
    this.genre,
  });

  @override
  List<Object?> get props => [
    id,
    title,
    owner,
    description,
    coverImage,
    author,
    language,
    edition,
    genre,
  ];
}

class SwapRequest extends Equatable {
  final String id;
  final String offeredBookId;
  final String requestedBookId;
  final String requester;
  final String owner;
  final String status;
  final String? notesFromRequester;
  final String? notesFromOwner;
  final String? proposedBookId;
  final bool? counterAcceptedByRequester;

  const SwapRequest({
    required this.id,
    required this.offeredBookId,
    required this.requestedBookId,
    required this.requester,
    required this.owner,
    required this.status,
    this.notesFromRequester,
    this.notesFromOwner,
    this.proposedBookId,
    this.counterAcceptedByRequester,
  });

  @override
  List<Object?> get props => [
    id,
    offeredBookId,
    requestedBookId,
    requester,
    owner,
    status,
    notesFromRequester,
    notesFromOwner,
    proposedBookId,
    counterAcceptedByRequester,
  ];
}