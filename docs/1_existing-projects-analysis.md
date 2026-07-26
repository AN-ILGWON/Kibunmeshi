# 1. 기존 프로젝트 분석서 / 既存プロジェクト分析書

> **작성일 / 作成日**: 202X-XX-XX  
> **버전 / バージョン**: 1.0

---

## 목차 / 目次

1. [하나마츠 (Hanmatsu) - JSP/Servlet 레거시](#1-하나마츠-hanmatsu)
2. [칸비 (Kanbee) - Spring Boot 기본](#2-칸비-kanbee)
3. [우마쿠지 (Umakuji) - 고급 풀스택](#3-우마쿠지-umakuji)
4. [종합 분석 및 성장 궤적](#4-종합-분석-및-성장-궤적)
5. [키분메시에 적용할 사항](#5-키분메시에-적용할-사항)

---

## 1. 하나마츠 (Hanmatsu)

### 1.1 개요 / 概要
전통적인 Java 웹 애플리케이션. JSP/Servlet 기반의 레거시 스택으로 구현.

### 1.2 기술 스택 / 技術スタック
| 구분 | 내용 |
|------|------|
| **Language** | Java 11 |
| **Framework** | JSP/Servlet |
| **Build Tool** | Maven |
| **Database** | Oracle (ojdbc8) |
| **Template** | JSP + JSTL |
| **Frontend** | jQuery, vanilla JS |
| **Deploy** | WAR (Tomcat/Jetty) |

### 1.3 주요 라이브러리 / 主要ライブラリ
- `org.json`: JSON 처리
- `jbcrypt`: 비밀번호 해싱
- `cos`: 파일 업로드

### 1.4 규모 / 規模
- Java 클래스: 58개 (58파일)
- JSP: 28개
- 특징: Controller/Model/Service/Util 계층 분리

### 1.5 분석 / 分析
- 전통적인 MVC 패턴의 정석적인 구조
- 현대적이지는 않지만, Java 웹의 기본기를 다지는 데 적합
- Oracle 사용으로 엔터프라이즈 환경 경험

---

## 2. 칸비 (Kanbee)

### 2.1 개요 / 概要
Spring Boot 기반의 현대적 웹 애플리케이션. Thymeleaf 템플릿과 Spring Security를 활용.

### 2.2 기술 스택 / 技術スタック
| 구분 | 내용 |
|------|------|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.4.0 |
| **Security** | Spring Security 6 |
| **Persistence** | MyBatis |
| **Database** | MySQL (mysql-connector-j) |
| **Template** | Thymeleaf + Spring Security Extras |
| **Build Tool** | Gradle |

### 2.3 주요 라이브러리 / 主要ライブラリ
- `thymeleaf-extras-springsecurity6`: Security와 Thymeleaf 연동
- `lombok`: 보일러플레이트 코드 감소
- i18n: 다국어 지원 (messages.properties)

### 2.4 특이사항 / 特記事項
- **AI 연동**: README에 OpenAI/Gemini 연동 언급

### 2.5 규모 / 規模
- Java 클래스: 26개 (24파일)
- Thymeleaf HTML: 18개
- MyBatis Mapper XML: 5개

### 2.6 분석 / 分析
- Spring Boot의 "정석"에 가까운 구조
- 보안(Spring Security)과 템플릿 엔진 통합 학습에 적합
- MyBatis로 SQL 제어 + Spring Data JPA와 비교 경험 가능

---

## 3. 우마쿠지 (Umakuji)

### 3.1 개요 / 概要
가장 규모가 크고 현대적인 풀스택 프로젝트. JWT 인증, Docker 배포, AI 연동까지 포함.

### 3.2 기술 스택 / 技術スタック
| 구분 | 내용 |
|------|------|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.2.5 |
| **Security** | Spring Security + JWT (JJWT) |
| **Validation** | Spring Validation |
| **Persistence** | MyBatis |
| **Database** | MySQL |
| **Template** | Thymeleaf |
| **Build Tool** | Gradle |

### 3.3 인프라/배포 / インフラ/デプロイ
- **Container**: Dockerfile 제공
- **Deploy**: deploy.sh (배포 스크립트)
- **DB Init**: schema.sql, data.sql

### 3.4 AI 연동 / AI連携
- **Library**: Google GenAI SDK (`com.google.genai`)
- 기능: AI 기반 기능 (가챠/추천 등)

### 3.5 규모 / 規模
- Java 클래스: 81개 (65파일) - 가장 큼
- Thymeleaf HTML: 11개
- MyBatis Mapper XML: 9개
- Domain: auth/gacha/recipe/ingredient/like/ranking/shop 등 다양한 도메인

### 3.6 특징 / 特徴
- **JWT 기반 인증**: 세션 대신 토큰 방식
- **모듈화된 구조**: global/config, domain별 패키지 분리
- **운영 고려**: Docker, 배포 스크립트, DB 마이그레이션

### 3.7 분석 / 分析
- 현대적 Java 백엔드의 "실무 레벨"을 경험
- JWT, Docker, AI 연동 등 트렌디한 기술 스택
- 프로덕션 배포까지 고려한 완성도

---

## 4. 종합 분석 및 성장 궤적

### 4.1 기술 스택 진화 / 技術スタックの進化
```
하나마츠 → 칸비 → 우마쿠지
JSP/Servlet → Spring Boot → Spring Boot + JWT + Docker
Oracle → MySQL → MySQL + Docker
단순 MVC → Spring Security → AI 연동
```

### 4.2 커버하는 영역 / カバーする領域
1. **레거시/전통적**: 하나마츠 (JSP, Servlet, Oracle)
2. **표준/현대적**: 칸비 (Spring Boot, Thymeleaf, MyBatis)
3. **고급/실무급**: 우마쿠지 (JWT, Docker, AI, 배포)

---

## 5. 키분메시에 적용할 사항

### 5.1 기술 스택 선택 / 技術スタック選択
| 구분 | 선택 | 이유 |
|------|------|------|
| **기반** | 우마쿠지와 동일 | Spring Boot 3.x + Thymeleaf + MyBatis |
| **인증** | Session (칸비 방식) | 2주 기간 고려, JWT보다 간단 |
| **AI** | 무료 버전 (DB 조합) | Gemini API 대신 DB 기반 랜덤 추천 |
| **배포** | Dockerfile 패턴 재사용 | 우마쿠지 방식 그대로 적용 |

### 5.2 규모 목표 / 規模目標
| 항목 | 목표 | 비고 |
|------|------|------|
| Java 클래스 | 15~20개 | 우마쿠지의 1/4 수준 |
| HTML 템플릿 | 5~8개 | Top, Category, Result, History, Login 등 |
| DB 테이블 | 4~5개 | emotions, categories, foods, users, recommendations |

### 5.3 핵심 차별점 / コア差別化
> "AI 없이도 자연스러운 감정-음식 매칭"
- 감정별/카테고리별 미리 준비된 데이터 조합
- 랜덤 추천이지만 "그럴듯한" 조언 템플릿 활용
- Unsplash 이미지로 시각적 완성도 확보

---

*문서 끝 / ドキュメント終了*
