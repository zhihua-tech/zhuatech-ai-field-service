# ZhuaTech AIFS｜AI现场服务调度系统

> 基于技能、位置、备件、SLA和安全要求生成可解释的现场服务派工方案

ZhuaTech AIFS 是知华科技（上海如静知华信息科技有限公司）发布的企业级源码项目，面向“客户报修、工单、技能、智能派工、路线、备件、作业许可、离线执行、验收与复盘”提供管理端与响应式业务端。工程采用前后端分离架构，所有示例数据均为虚构数据。

[知华科技官网](https://www.zhuatech.cn/) · [架构说明](docs/ARCHITECTURE.md) · [API 文档](docs/API.md) · [企业能力](docs/ENTERPRISE.md) · [测试说明](docs/TESTING.md)

![AI现场服务调度系统产品界面示意](docs/images/product-overview.svg)

## 业务模块

| 模块 | 核心能力 |
| --- | --- |
| 服务请求 | 受理设备故障、客户影响、位置和服务承诺 |
| 工单中心 | 生成优先级、任务步骤、责任和 SLA |
| 技能与认证 | 维护技师技能、资质、班次和服务区域 |
| AI智能派工 | 综合技能、距离、负载、备件和客户时间窗派工 |
| 路线优化 | 生成多工单路线并处理交通和紧急插单 |
| 服务备件 | 检查库存、车载库、替代件和紧急配送 |
| 作业安全 | 校验许可、风险分析、隔离和个人防护 |
| 移动执行 | 支持离线工单、扫码、照片、签名和工时 |
| 一次修复与复盘 | 跟踪一次修复率、返工、满意度和模型偏差 |

![AI现场服务调度系统业务闭环](docs/images/workflow.svg)

## 企业级控制

- 新增多工单派工引擎：综合区域、技能、随车备件、实时负载、路程与 SLA，给出派工及未派原因；
- ADMIN / OPERATOR 角色边界和管理员接口隔离；
- 服务端字段、模块、唯一编号和状态迁移校验；
- 组织、期间、责任人、风险等级、到期日和 SLA 统计；
- 幂等创建、JPA 乐观锁、重复提交保护和职责分离；
- 附件 SHA-256 元数据、业务凭证完整性与全流程审计；
- 组合检索、分页、逾期筛选、UTF-8 CSV 导出和协作时间线；
- 外部系统仅预留适配器，使用方自行配置地址与凭据；
- prod profile 拒绝默认密码、弱数据库口令和本地跨域来源。

## 技术架构

- 后端：Java 21、Spring Boot、Spring Security、JPA、Bean Validation、Actuator
- 前端：Vue 3、Vite、Axios，支持桌面端与移动端响应式布局
- 数据库：MySQL 8；自动化测试使用 H2
- 交付：Docker Compose、Nginx、环境变量、GitHub Actions
- Java 包名：`cn.zhuatech.aifieldservice`

## 启动与测试

```bash
cd backend && mvn test
cd ../frontend && npm install && npm run build
cd .. && cp .env.example .env && docker compose up --build
```

开发演示账号：`admin / admin123`、`operator / operator123`。生产环境必须通过环境变量替换全部默认凭据。

## 许可与商业授权

Copyright © 2026 上海如静知华信息科技有限公司。

本工程仅允许个人学习、研究和非商业技术交流，**不得用于商业用途**。企业内部使用、生产部署、SaaS运营、项目交付、品牌替换、收费培训、咨询实施或再分发，均须事先获得上海如静知华信息科技有限公司书面授权，详见 [LICENSE](LICENSE)。

深度开发、私有化部署、系统集成与企业数字化咨询，请访问[知华科技官网](https://www.zhuatech.cn/)或扫码联系：

| 微信咨询一 | 微信咨询二 |
| --- | --- |
| ![微信咨询二维码一](docs/images/zhuatech-wechat-consulting.png) | ![微信咨询二维码二](docs/images/zhuatech-wechat-consulting-2.png) |

SEO：AI现场服务调度系统、AIFS系统源码、企业数字化、Java企业系统、Vue管理系统、知华科技、上海如静知华信息科技有限公司。
