package com.codepresso.meu.service;

import com.codepresso.meu.mapper.PostMapper;
import com.codepresso.meu.vo.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private PostMapper postMapper;

    public List<Post> getPostByFeed(Integer id) {
        return postMapper.findByFeed(id);
    }

    public Boolean savePost(Post post) {
        Integer result = postMapper.save(post);
        return result == 1;
    }

    public Boolean updatePost(Post post) {
        Integer result = postMapper.update(post);
        return result == 1;
    }

    public Boolean deletePost(Integer id) {
        Integer result = postMapper.delete(id);
        return result == 1;
    }
}
