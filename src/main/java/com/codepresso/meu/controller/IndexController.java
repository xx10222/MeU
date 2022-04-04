package com.codepresso.meu.controller;

import com.codepresso.meu.controller.dto.CommentResponseDto;
import com.codepresso.meu.controller.dto.FeedRequestDto;
import com.codepresso.meu.controller.dto.PostResponseDto;
import com.codepresso.meu.service.CommentService;
import com.codepresso.meu.service.PostService;
import com.codepresso.meu.service.UserService;
import com.codepresso.meu.vo.Comment;
import com.codepresso.meu.vo.FeedItem;
import com.codepresso.meu.vo.Post;
import com.codepresso.meu.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    private PostService postService;
    private CommentService commentService;
    private UserService userService;


    public IndexController(PostService postService, CommentService commentService, UserService userService) {
        this.postService = postService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @RequestMapping(value= "/")
    public String index(Model model, @RequestBody(required = false) FeedRequestDto feedDto,
                        @CookieValue(value = "page",required = false) String currentPage, HttpServletResponse response, HttpServletRequest request) {
        List<FeedItem> feedItems = new ArrayList<>();
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        List<Post> postList = new ArrayList<>();
        boolean isFinalPage = false;

        if (feedDto == null) feedDto = new FeedRequestDto(1,0);
        if(currentPage == null) {
            currentPage = "1";
            Cookie cookie = new Cookie("page", currentPage);
            cookie.setMaxAge(60 * 60 * 24);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        Integer maxSearchPostCnt = postService.getAllPost().size();
        if(postService.getViewPostSize() * Integer.parseInt(currentPage) > maxSearchPostCnt) {isFinalPage = true;}

        postList = postService.getPostByPage(Integer.parseInt(currentPage));
        for(Post post : postList) {
            postResponseDtos.add(new PostResponseDto(post));
            List<Comment> commentList = commentService.getCommentListByPostInFeed(post.getPostId(), 1);
            FeedItem feeditem = new FeedItem(new PostResponseDto(post), commentList);
            feeditem.setLikeCnt(postService.getLikesOfPost(post.getPostId()).size());
            feeditem.setCommentCnt(commentService.getCommentsOfPost(post.getPostId()));
            feedItems.add(feeditem);
        }
        model.addAttribute("isFinalPage", isFinalPage);
        model.addAttribute("feedItems", feedItems);

        //전체 사용자 조회 및 팔로우 추천
        List<User> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
        return "index";
    }

    // Trending Page
    @RequestMapping(value = "/trending")
    public String getTrendingPage() {
        return "tags";
    }

    // Explore Page
    @RequestMapping(value = "/explore")
    public String getExplorePage() {
        return "explore";
    }

    // Fag Page
    @RequestMapping(value = "/faq")
    public String getFaqPage() {
        return "faq";
    }

    // Contact Page
    @RequestMapping(value = "/contact")
    public String getContactPage() {
        return "contact";
    }


}
