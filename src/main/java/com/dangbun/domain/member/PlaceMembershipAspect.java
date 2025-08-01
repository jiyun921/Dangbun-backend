package com.dangbun.domain.member;

import com.dangbun.domain.member.entity.Member;
import com.dangbun.domain.member.exception.custom.PlaceAccessDeniedException;
import com.dangbun.domain.member.repository.MemberRepository;
import com.dangbun.domain.member.response.status.MemberExceptionResponse;
import com.dangbun.domain.user.entity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.nio.file.AccessDeniedException;

import static com.dangbun.domain.member.response.status.MemberExceptionResponse.*;

@Aspect
@Component
@RequiredArgsConstructor
public class PlaceMembershipAspect {
    private final MemberRepository memberRepository;


    @Before("@within(com.dangbun.domain.member.CheckPlaceMembership) || @annotation(com.dangbun.domain.member.CheckPlaceMembership)")
    public void validate(JoinPoint joinPoint) throws  AccessDeniedException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof CustomUserDetails userDetails)) {
            throw new AccessDeniedException("인증되지 않은 사용자입니다.");
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        CheckPlaceMembership checkPlaceMembership = method.getAnnotation(CheckPlaceMembership.class);

        if (checkPlaceMembership == null) {
            checkPlaceMembership = joinPoint.getTarget().getClass().getAnnotation(CheckPlaceMembership.class);
        }

        if (checkPlaceMembership == null) {
            throw new IllegalStateException("CheckPlaceMembership 애너테이션을 찾을 수 없습니다.");
        }

        String placeIdParamName = checkPlaceMembership.placeIdParam();
        String[] paramNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        Long placeId = null;
        for (int i = 0; i < paramNames.length; i++) {
            if (paramNames[i].equals(placeIdParamName) && args[i] instanceof Long) {
                placeId = (Long) args[i];
                break;
            }
        }

        if (placeId == null) {
            throw new IllegalArgumentException("placeId 파라미터가 존재하지 않거나 Long 타입이 아닙니다.");
        }

        Member member = memberRepository.findByUser_UserIdAndPlace_PlaceId(userDetails.getUser().getUserId(), placeId)
                .orElseThrow(()->new PlaceAccessDeniedException(PLACE_ACCESS_DENIED));

        MemberContext.set(member);
    }

    @After("@within(com.dangbun.domain.member.CheckPlaceMembership) || @annotation(com.dangbun.domain.member.CheckPlaceMembership)")
    public void clearMemberContext() {
        MemberContext.clear();
    }
}
