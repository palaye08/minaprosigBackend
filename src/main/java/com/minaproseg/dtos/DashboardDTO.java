package com.minaproseg.dtos;

import com.minaproseg.enums.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DashboardDTO {
    private ProfileRole profile;
    private DashboardStatsDTO stats;
    private List<ActiviteDTO> prochainesActivites;
    private List<ObjectifDTO> objectifsActifs;
    private List<SessionCoachingDTO> prochainsSessions;
    private List<DocumentDTO> documentsRecents;
}