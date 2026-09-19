/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aifieldservice.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class DispatchOptimizationService {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public DispatchResult dispatch(@Valid DispatchRequest request) {
        Set<String> technicianIds = new HashSet<>();
        for (Technician tech : request.technicians()) if (!technicianIds.add(tech.technicianId())) throw new IllegalArgumentException("工程师编号不能重复: " + tech.technicianId());
        Set<String> orderIds = new HashSet<>();
        for (WorkOrder order : request.workOrders()) if (!orderIds.add(order.workOrderNo())) throw new IllegalArgumentException("工单号不能重复: " + order.workOrderNo());
        Map<String,Integer> load = new HashMap<>();
        Map<String,LocalDateTime> available = new HashMap<>();
        request.technicians().forEach(tech -> {load.put(tech.technicianId(),0);available.put(tech.technicianId(),tech.availableAt());});
        List<WorkOrder> orders = request.workOrders().stream().sorted(Comparator.comparingInt(WorkOrder::priority).reversed()
            .thenComparing(WorkOrder::slaDueAt).thenComparing(WorkOrder::workOrderNo)).toList();
        List<DispatchLine> assignments = new ArrayList<>(); List<UnassignedOrder> unassigned = new ArrayList<>();
        for (WorkOrder order : orders) {
            Candidate best = null;
            for (Technician tech : request.technicians()) {
                if (load.get(tech.technicianId()) >= tech.maxJobs()) continue;
                if (!tech.regions().contains(order.region()) || !tech.skills().contains(order.requiredSkill())) continue;
                if (!tech.stockParts().containsAll(order.requiredParts())) continue;
                LocalDateTime start = available.get(tech.technicianId()).isAfter(request.planAt()) ? available.get(tech.technicianId()) : request.planAt();
                start = start.plusMinutes(tech.travelMinutes());
                LocalDateTime finish = start.plusMinutes(Math.round(order.serviceHours()*60));
                long slaRisk = Math.max(0, Duration.between(order.slaDueAt(), finish).toMinutes());
                int score = (int)Math.max(0, 100 - tech.travelMinutes()/2 - load.get(tech.technicianId())*12 - Math.min(40, slaRisk/10));
                Candidate candidate = new Candidate(tech,start,finish,slaRisk,score);
                if (best==null || candidate.score()>best.score() || candidate.score()==best.score() && finish.isBefore(best.finish())) best=candidate;
            }
            if (best==null) {unassigned.add(new UnassignedOrder(order.workOrderNo(), "无同时满足区域、技能、备件与负载上限的工程师"));continue;}
            Technician tech=best.technician(); load.compute(tech.technicianId(),(key,value)->value+1); available.put(tech.technicianId(),best.finish());
            assignments.add(new DispatchLine(order.workOrderNo(),tech.technicianId(),best.start(),best.finish(),best.score(),
                best.slaLateMinutes()==0?"WITHIN_SLA":"SLA_RISK",best.slaLateMinutes(),"区域、技能、备件和实时负载综合最优"));
        }
        long slaRisks=assignments.stream().filter(line->line.slaLateMinutes()>0).count();
        return new DispatchResult(assignments,unassigned,load,assignments.size(),unassigned.size(),slaRisks);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private record Candidate(Technician technician, LocalDateTime start, LocalDateTime finish, long slaLateMinutes, int score) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record DispatchRequest(@NotNull LocalDateTime planAt, @NotEmpty List<@Valid Technician> technicians,
                                  @NotEmpty List<@Valid WorkOrder> workOrders) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Technician(@NotBlank String technicianId, @NotEmpty Set<@NotBlank String> skills,
                             @NotEmpty Set<@NotBlank String> regions, Set<@NotBlank String> stockParts,
                             @NotNull LocalDateTime availableAt, @Min(1) int maxJobs, @Min(0) int travelMinutes) {
        /**
         * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
         */
        public Technician { stockParts = stockParts == null ? Set.of() : Set.copyOf(stockParts); }
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record WorkOrder(@NotBlank String workOrderNo, @NotBlank String region, @NotBlank String requiredSkill,
                            Set<@NotBlank String> requiredParts, @NotNull LocalDateTime slaDueAt,
                            @DecimalMin("0.1") double serviceHours, @Min(1) @Max(100) int priority) {
        /**
         * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
         */
        public WorkOrder { requiredParts = requiredParts == null ? Set.of() : Set.copyOf(requiredParts); }
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record DispatchLine(String workOrderNo, String technicianId, LocalDateTime plannedStart, LocalDateTime plannedFinish,
                               int matchScore, String slaStatus, long slaLateMinutes, String reason) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record UnassignedOrder(String workOrderNo, String reason) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record DispatchResult(List<DispatchLine> assignments, List<UnassignedOrder> unassigned,
                                 Map<String,Integer> technicianLoad, int assignedCount, int unassignedCount, long slaRiskCount) {}
}
