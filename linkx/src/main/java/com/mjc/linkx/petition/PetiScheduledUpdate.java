package com.mjc.linkx.petition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Component
public class PetiScheduledUpdate {
    @Autowired
    private PetitionServiceImpl petitionService;

    // 자정마다 실행되도록 스케줄링 설정(현재는 테스트를 위해 1분마다 변경하도록 수정)
    @Scheduled(cron = "0 0/1 * * * *")
    public void updatePetitonStatuses() {
        LocalDate today = LocalDate.now();

        // 특정 형식에 맞게 포맷터를 설정합니다.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // try-catch 블록 외부에서 선언하여 오류 메시지를 출력할 때 사용
        PetitionDto petition = null;
        try {
            // 모든 청원 가져오기
            List<PetitionDto> petitions = petitionService.findAll();

            // 각 청원을 순회하면서 상태 업데이트
            for (PetitionDto currentPetition : petitions) {
                // 현재 청원을 외부에서 선언된 변수에 할당
                petition = currentPetition;

                // 종료일이 문자열이므로 LocalDate로 변환합니다.
                LocalDate endDt = LocalDate.parse(petition.getEndDt(), formatter);

                boolean newPlayingStatus = !endDt.isBefore(today) && !endDt.isEqual(today);
                petitionService.updatePlaying(petition.getId(), newPlayingStatus); // DB에 반영
            }
        } catch (DateTimeParseException e) {
            System.out.println("날짜 형식 오류: " + (petition != null ? petition.getEndDt() : "알 수 없는 날짜"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
