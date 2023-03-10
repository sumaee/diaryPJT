package com.example.project.recentdiary.controller;

import com.example.project.recentdiary.model.dto.RecentDiaryResponseDto;
import com.example.project.recentdiary.model.service.RecentDiaryService;
import com.example.project.user.model.jwt.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.example.project.user.model.jwt.JwtProperties.TOKEN_HEADER;

@RestController
@RequiredArgsConstructor
@Api(tags = {"팔로잉의 최근 일기(스토리) API"})
public class RecentDiaryController {
    private final RecentDiaryService recentDiaryService;
    private final JwtUtil jwtUtil;

    @GetMapping("/recent")
    @ApiOperation(value = "팔로잉 최근 다이어리(스토리)", notes = "팔로잉들의 가장 최근 일기 중, 공개이면서 읽지 않은 일기 목록 최신순으로 반환(최대 20개)")
    public ResponseEntity<List<RecentDiaryResponseDto>> selectAllRecentDiary(HttpServletRequest request) {

        Long userId = jwtUtil.getUserId(request.getHeader(TOKEN_HEADER));
        List<RecentDiaryResponseDto> recentDiaryList = recentDiaryService.selectAllRecentDiary(userId);
        if (recentDiaryList == null) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        return new ResponseEntity<>(recentDiaryList, HttpStatus.OK);
    }


}
