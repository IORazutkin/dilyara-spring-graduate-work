package com.dilyara.graduatework.dto.facebook;

import com.dilyara.graduatework.entity.InstagramPost;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostListDto {
  List<InstagramPostDto> data;
  PagingDto paging;

  public List<InstagramPost> getInstagramPostList () {
    return data.stream().map(InstagramPostDto::toInstagramPost).collect(Collectors.toList());
  }

  public InstagramPostListDto toInstagramPostListDto () {
    InstagramPostListDto instagramPostListDto = new InstagramPostListDto();
    instagramPostListDto.setPosts(getInstagramPostList());

    if (paging.getNext() != null) {
      instagramPostListDto.setAfter(paging.getCursors().getAfter());
    }

    return instagramPostListDto;
  }
}
