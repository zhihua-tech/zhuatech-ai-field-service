/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aifieldservice.domain;
import org.springframework.stereotype.Component;
import java.util.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Component
public class DomainCatalog {
    private final Map<String, WorkflowAction> actions = new LinkedHashMap<>();
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public DomainCatalog() {
        actions.put("DISPATCH", new WorkflowAction("DISPATCH", "确认智能派工", List.of("草稿"), "已派工", "OPERATOR"));
        actions.put("VERIFY", new WorkflowAction("VERIFY", "提交现场验收", List.of("已派工"), "待关闭", "ADMIN"));
        actions.put("CLOSE", new WorkflowAction("CLOSE", "确认工单关闭", List.of("待关闭"), "已关闭", "ADMIN"));
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String systemName() { return "知华科技AI现场服务调度系统"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String scene() { return "客户报修、工单、技能、智能派工、路线、备件、作业许可、离线执行、验收与复盘"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String initialStatus() { return "草稿"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String partyLabel() { return "客户/设备/服务工单"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String amountLabel() { return "服务成本"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String quantityLabel() { return "现场任务数"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String dueLabel() { return "SLA期限"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public List<ModuleDefinition> modules() { return List.of(
            new ModuleDefinition("REQUEST", "服务请求", "受理设备故障、客户影响、位置和服务承诺"),
            new ModuleDefinition("WORK_ORDER", "工单中心", "生成优先级、任务步骤、责任和 SLA"),
            new ModuleDefinition("SKILL", "技能与认证", "维护技师技能、资质、班次和服务区域"),
            new ModuleDefinition("DISPATCH", "AI智能派工", "综合技能、距离、负载、备件和客户时间窗派工"),
            new ModuleDefinition("ROUTE", "路线优化", "生成多工单路线并处理交通和紧急插单"),
            new ModuleDefinition("PARTS", "服务备件", "检查库存、车载库、替代件和紧急配送"),
            new ModuleDefinition("SAFETY", "作业安全", "校验许可、风险分析、隔离和个人防护"),
            new ModuleDefinition("MOBILE", "移动执行", "支持离线工单、扫码、照片、签名和工时"),
            new ModuleDefinition("QUALITY", "一次修复与复盘", "跟踪一次修复率、返工、满意度和模型偏差")
        ); }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Map<String, WorkflowAction> actions() { return Collections.unmodifiableMap(actions); }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record ModuleDefinition(String code,String name,String description) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record WorkflowAction(String code,String label,List<String> from,String to,String requiredRole) {}
}
