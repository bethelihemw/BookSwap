class SwapRequestModel {
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

  SwapRequestModel({
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

  factory SwapRequestModel.fromJson(Map<String, dynamic> json) {
    return SwapRequestModel(
      id: json['_id'] ?? json['id'],
      offeredBookId: json['offeredBook']['_id'] ?? json['offeredBook'],
      requestedBookId: json['requestedBook']['_id'] ?? json['requestedBook'],
      requester: json['requester']['email'] ?? json['requester'],
      owner: json['owner']['email'] ?? json['owner'],
      status: json['status'],
      notesFromRequester: json['notesFromRequester'],
      notesFromOwner: json['notesFromOwner'],
      proposedBookId: json['proposedBookFromOwner']?['_id'] ?? json['proposedBookFromOwner'],
      counterAcceptedByRequester: json['counterAcceptedByRequester'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'offeredBookId': offeredBookId,
      'requestedBookId': requestedBookId,
      'requester': requester,
      'owner': owner,
      'status': status,
      'notesFromRequester': notesFromRequester,
      'notesFromOwner': notesFromOwner,
      'proposedBookId': proposedBookId,
      'counterAcceptedByRequester': counterAcceptedByRequester,
    };
  }
}