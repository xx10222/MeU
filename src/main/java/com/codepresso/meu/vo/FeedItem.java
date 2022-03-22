package com.codepresso.meu.vo;

import com.codepresso.meu.controller.dto.CommentInfo;
import com.codepresso.meu.controller.dto.CommentResponseDto;
import com.codepresso.meu.controller.dto.PostResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class FeedItem {
    Integer postId;
    Integer userId;
    String content;
    Date createdAt;
    Date updatedAt;
    String email;
    String nickname;
    List<Comment> commentListByPost = new ArrayList<>();

    public FeedItem(PostResponseDto post, List<Comment> commentList){
        this.postId = post.getPostId();
        this.userId = post.getUserId();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.email = post.getEmail();
        this.nickname = post.getNickname();
        this.commentListByPost = commentList;
    }


}