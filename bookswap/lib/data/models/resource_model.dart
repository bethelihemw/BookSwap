class ResourceModel {
  final String id;
  final String title;
  final String owner;
  final String description;
  final String? coverImage;
  final String? author;
  final String? language;
  final String? edition;
  final String? genre;

  ResourceModel({
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

  factory ResourceModel.fromJson(Map<String, dynamic> json) {
    return ResourceModel(
      id: json['_id'] ?? json['id'],
      title: json['title'],
      owner: json['owner']['email'] ?? json['owner'],
      description: json['description'],
      coverImage: json['coverImage'],
      author: json['author'],
      language: json['language'],
      edition: json['edition'],
      genre: json['genre'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'title': title,
      'owner': owner,
      'description': description,
      'coverImage': coverImage,
      'author': author,
      'language': language,
      'edition': edition,
      'genre': genre,
    };
  }
}