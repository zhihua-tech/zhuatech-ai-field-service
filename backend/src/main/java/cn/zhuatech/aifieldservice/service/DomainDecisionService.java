/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aifieldservice.service;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Service;
import java.util.*;
@Service public class DomainDecisionService {
 public DecisionResult assess(DecisionRequest request) { int score=100;List<String>actions=new ArrayList<>();if(request.technicianSkillMatch()<85){score-=40;actions.add("重新匹配具备设备资质的技师");}if(request.partsAvailabilityRate()<90){score-=30;actions.add("补齐备件或安排替代件审批");}if(request.travelMinutes()>request.slaRemainingMinutes()){score-=45;actions.add("改派更近技师或升级SLA风险");}if(request.firstTimeFixProbability()<70){score-=20;actions.add("补充远程专家和诊断资料");}if(request.safetyPermitRequired()&&!request.safetyPermitApproved()){score-=70;actions.add("作业许可批准前禁止现场开工");}if("P1".equals(request.priority())&&request.slaRemainingMinutes()<60){score-=15;actions.add("启动P1应急响应与客户通知");}return result(score,actions,"DISPATCH","REPLAN","BLOCKED",Map.of("skillMatch",request.technicianSkillMatch(),"partsAvailability",request.partsAvailabilityRate(),"firstTimeFixProbability",request.firstTimeFixProbability(),"travelMinutes",request.travelMinutes(),"slaRemainingMinutes",request.slaRemainingMinutes())); }
 private DecisionResult result(int raw,List<String> actions,String good,String warn,String bad,Map<String,Object> metrics) { int score=Math.max(0,Math.min(100,raw));String decision=score>=80?good:score>=50?warn:bad;return new DecisionResult(decision,score,metrics,List.copyOf(actions)); }
 private DecisionResult riskResult(int raw,List<String> actions,String good,String warn,String bad,Map<String,Object> metrics) { int score=Math.max(0,Math.min(100,raw));String decision=score>=70?bad:score>=40?warn:good;return new DecisionResult(decision,score,metrics,List.copyOf(actions)); }
 public record DecisionRequest(
        @NotBlank String workOrderNo,
        @Pattern(regexp="P[1-4]") String priority,
        @PositiveOrZero int travelMinutes,
        @PositiveOrZero int slaRemainingMinutes,
        @DecimalMin("0") @DecimalMax("100") double technicianSkillMatch,
        @DecimalMin("0") @DecimalMax("100") double partsAvailabilityRate,
        @DecimalMin("0") @DecimalMax("100") double firstTimeFixProbability,
        boolean safetyPermitRequired,
        boolean safetyPermitApproved) {}
 public record DecisionResult(String decision,int score,Map<String,Object> metrics,List<String> actions) {}
}
