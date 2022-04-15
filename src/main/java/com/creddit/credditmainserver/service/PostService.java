package com.creddit.credditmainserver.service;

import com.creddit.credditmainserver.domain.Member;
import com.creddit.credditmainserver.domain.Post;
import com.creddit.credditmainserver.dto.request.PostRequestDto;
import com.creddit.credditmainserver.dto.response.PostResponseDto;
import com.creddit.credditmainserver.login.security.SecurityUtil;
import com.creddit.credditmainserver.repository.MemberRepository;
import com.creddit.credditmainserver.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createPost(PostRequestDto postRequestDto){
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.getById(currentMemberId);

        return postRepository.save(postRequestDto.toEntity(member)).getId();
    }

    @Transactional
    public Long updatePost(Long id, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다. id = " + id));

        post.updatePost(postRequestDto.getTitle(), postRequestDto.getContent(), postRequestDto.getImgName());

        return id;
    }

    @Transactional
    public void deletePost(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다. id = " + id));

        postRepository.delete(post);
    }

    public List<PostResponseDto> findAllPost(){
        return postRepository.findAll().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    public PostResponseDto findById(Long id) {
        Post entity = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다. id = " + id));

        return new PostResponseDto(entity);
    }
}
