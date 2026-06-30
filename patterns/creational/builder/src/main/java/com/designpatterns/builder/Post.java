package com.designpatterns.builder;

public class Post {

    // Final fields guarantee complete thread-safe immutability after construction
    private final String title;
    private final String content;
    private final String author;
    private final String category;
    private final String imagePreviewUrl;

    // The package-private/private constructor receives the configured Builder instance
    private Post(Builder builder) {
        this.title = builder.title;
        this.content = builder.content;
        this.author = builder.author;
        this.category = builder.category;
        this.imagePreviewUrl = builder.imagePreviewUrl;
    }

    // Expose only getters to enforce read-only state
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getImagePreviewUrl() {
        return imagePreviewUrl;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", imagePreviewUrl='" + imagePreviewUrl + '\'' +
                '}';
    }

    // Static inner class for the Builder
    public static class Builder {
        private String title;
        private String content;
        private String author;
        private String category;
        private String imagePreviewUrl;

        // Setter-like methods that return 'this' to facilitate a fluid API interface
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder imagePreviewUrl(String imagePreviewUrl) {
            this.imagePreviewUrl = imagePreviewUrl;
            return this;
        }

        /**
         * The orchestrating method that validates internal state constraints
         * before bringing the final Product object into lifecycle existence.
         *
         * @return the Post object
         */
        public Post build() {
            // Business Validation Rules
            if (this.title == null || this.title.trim().isEmpty()) {
                throw new IllegalStateException("Validation Error: Title is a required field.");
            }
            if (this.content == null || this.content.trim().isEmpty()) {
                throw new IllegalStateException("Validation Error: Content is a required field.");
            }
            if (this.author == null || this.author.trim().isEmpty()) {
                throw new IllegalStateException("Validation Error: Author is a required field.");
            }

            // Safe instantiation of the final Product object
            return new Post(this);
        }
    }

}
