/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aifieldservice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest @AutoConfigureMockMvc class DispatchOptimizationApiTests {
 @Autowired MockMvc mvc;
 private static final String BODY="""
  {"planAt":"2026-09-20T08:00:00","technicians":[
   {"technicianId":"T-01","skills":["PLC"],"regions":["上海"],"stockParts":["P-01"],"availableAt":"2026-09-20T08:00:00","maxJobs":2,"travelMinutes":20},
   {"technicianId":"T-02","skills":["NETWORK"],"regions":["上海"],"stockParts":[],"availableAt":"2026-09-20T08:00:00","maxJobs":1,"travelMinutes":10}],
   "workOrders":[{"workOrderNo":"WO-1","region":"上海","requiredSkill":"PLC","requiredParts":["P-01"],"slaDueAt":"2026-09-20T12:00:00","serviceHours":2,"priority":100},
                 {"workOrderNo":"WO-2","region":"苏州","requiredSkill":"PLC","requiredParts":[],"slaDueAt":"2026-09-20T11:00:00","serviceHours":1,"priority":80}]}
  """;
 @Test void assignsBySkillsPartsRegionAndCapacity() throws Exception {
  mvc.perform(post("/api/domain/dispatch-plan").with(httpBasic("operator","operator123")).contentType(MediaType.APPLICATION_JSON).content(BODY))
   .andExpect(status().isOk()).andExpect(jsonPath("$.data.assignedCount").value(1)).andExpect(jsonPath("$.data.unassignedCount").value(1))
   .andExpect(jsonPath("$.data.assignments[0].technicianId").value("T-01")).andExpect(jsonPath("$.data.assignments[0].slaStatus").value("WITHIN_SLA"));
 }
 @Test void rejectsDuplicateTechnician() throws Exception {
  String duplicate=BODY.replace("\"technicianId\":\"T-02\"","\"technicianId\":\"T-01\"");
  mvc.perform(post("/api/domain/dispatch-plan").with(httpBasic("operator","operator123")).contentType(MediaType.APPLICATION_JSON).content(duplicate))
   .andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value("工程师编号不能重复: T-01"));
 }
 @Test void dispatchRequiresAuthentication() throws Exception {mvc.perform(post("/api/domain/dispatch-plan").contentType(MediaType.APPLICATION_JSON).content(BODY)).andExpect(status().isUnauthorized());}
}
