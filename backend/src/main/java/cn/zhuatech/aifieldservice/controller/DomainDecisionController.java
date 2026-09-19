/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aifieldservice.controller;
import cn.zhuatech.aifieldservice.common.ApiResponse;
import cn.zhuatech.aifieldservice.service.DomainDecisionService;
import cn.zhuatech.aifieldservice.service.DispatchOptimizationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/domain") public class DomainDecisionController {
 private final DomainDecisionService service; private final DispatchOptimizationService dispatchOptimizationService;
 public DomainDecisionController(DomainDecisionService service, DispatchOptimizationService dispatchOptimizationService){this.service=service;this.dispatchOptimizationService=dispatchOptimizationService;}
 @PostMapping("/decision") public ApiResponse<DomainDecisionService.DecisionResult> assess(@Valid @RequestBody DomainDecisionService.DecisionRequest request){return ApiResponse.ok(service.assess(request));}
 @PostMapping("/dispatch-plan") public ApiResponse<DispatchOptimizationService.DispatchResult> dispatch(@Valid @RequestBody DispatchOptimizationService.DispatchRequest request){return ApiResponse.ok(dispatchOptimizationService.dispatch(request));}
}
