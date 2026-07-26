<div align="center">

# 🍽️ きぶんめし (Kibunmeshi)

**今日の気分を選ぶだけで、ぴったりのごはんと心に寄り添う一言をお届けするWebサービス**

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-green)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)](https://www.postgresql.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

![Demo](https://via.placeholder.com/800x400/FFD700/000000?text=きぶんめし+Demo+Screenshot)

</div>

---

## 📖 日本語

### 🎯 サービス概要

**きぶんめし**は、ユーザーの現在の気分に合わせて最適な料理を推薦し、心に寄り添う一言を届けるWebサービスです。

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│   😊 気分を選択 → 🍽️ カテゴリを選択 → 🎲 料理推薦           │
│                                                             │
│              💬 心に寄り添う一言と一緒に                     │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

- **開発期間**: 2週間（14日間）
- **ターゲットユーザー**: 日本人
- **コア機能**: 気分選択 + 料理カテゴリ選択 → 料理推薦 + アドバイス提供

### ✨ 主な機能

| 機能 | 説明 |
|------|------|
| **😊 気分選択** | 6種類の気分（うれしい、かなしい、おこ、つかれた、わくわく、おだやか）から選択 |
| **🍽️ カテゴリ選択** | 韓国料理、洋食、中華、和食、その他から選択 |
| **🎲 料理推薦** | 選択した気分とカテゴリに合った料理をランダム推薦 |
| **💬 アドバイス表示** | 料理に合わせた心に寄り添う一言を表示 |
| **📸 料理画像** | Unsplash APIで美しい料理写真を表示 |
| **🔄 再推薦** | 同じ条件で別の料理を再推薦 |

### 🛠️ 技術スタック

```
┌─────────────────────────────────────────────────────────────┐
│                        フロントエンド                         │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐     │
│  │  Thymeleaf  │    │   HTML5     │    │    CSS3     │     │
│  └─────────────┘    └─────────────┘    └─────────────┘     │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                       バックエンド                           │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐     │
│  │Spring Boot  │    │Spring       │    │  MyBatis    │     │
│  │   3.2.5      │    │ Security    │    │             │     │
│  └─────────────┘    └─────────────┘    └─────────────┘     │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                        データベース                          │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐     │
│  │ PostgreSQL  │    │   MySQL     │    │  Unsplash   │     │
│  │    15       │    │   8.0       │    │    API      │     │
│  └─────────────┘    └─────────────┘    └─────────────┘     │
│                                              │                │
│                                              ▼                │
│                                    ┌─────────────┐           │
│                                    │  Gemini     │           │
│                                    │    API      │           │
│                                    └─────────────┘           │
└─────────────────────────────────────────────────────────────┘
```

| カテゴリ | 技術 |
|---------|------|
| **言語** | Java 17 |
| **フレームワーク** | Spring Boot 3.2.5 |
| **テンプレート** | Thymeleaf |
| **データベース** | PostgreSQL 15 / MySQL 8.0 |
| **ORM** | MyBatis |
| **セキュリティ** | Spring Security |
| **ビルドツール** | Gradle 8.x |
| **外部API** | Unsplash (料理画像), Gemini API (アドバイス生成) |

### 📊 データベース構造

- **emotions**: 6種類の気分マスター
- **food_categories**: 5種類の料理カテゴリマスター
- **foods**: 気分×カテゴリの組み合わせ（90〜120品）
- **food_advice_templates**: アドバイステンプレート

### 🚀 セットアップ

#### 前提条件
- Java 17以上
- PostgreSQL 15以上 または MySQL 8.0以上
- Gradle 8.x

#### イ ンストール手順

1. **リポジトリのクローン**
```bash
git clone https://github.com/AN-ILGWON/Kibunmeshi.git
cd Kibunmeshi
```

2. **データベースの作成**
```sql
-- PostgreSQLの場合
CREATE DATABASE kibunmeshi;

-- MySQLの場合
CREATE DATABASE kibunmeshi CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. **application.ymlの設定**
```yaml
spring:
  datasource:
    # PostgreSQLの場合
    url: jdbc:postgresql://localhost:5432/kibunmeshi
    # MySQLの場合
    # url: jdbc:mysql://localhost:3306/kibunmeshi
    username: your_username
    password: your_password
```

4. **APIキーの取得**
- [Unsplash Developers](https://unsplash.com/developers) でAPIキーを取得
- [Google AI Studio](https://makersuite.google.com/app/apikey) でGemini APIキーを取得
- `application.yml` に設定

5. **アプリケーションの起動**
```bash
./gradlew bootRun
```

6. **アクセス**
- ブラウザで `http://localhost:8080` にアクセス

### 📱 画面遷移

```
┌─────────────┐
│  トップ画面  │  気分を選択
│  (気分選択)  │ ──────────►
└─────────────┘
                │
                ▼
┌─────────────┐
│ カテゴリ選択 │  カテゴリを選択
│             │ ──────────►
└─────────────┘
                │
                ▼
┌─────────────┐
│  結果画面    │  料理推薦 + アドバイス
│             │  再推薦 / 最初から
└─────────────┘
```

### 🎨 デザイン特徴

- **モバイルファースト**: 375px〜1920pxに対応
- **レスポンシブデザイン**: モバイル、タブレット、デスクトップに対応
- **カラフルなUI**: 気分ごとに異なる色を使用
- **シンプルなUX**: 2クリックで結果確認

### 🔮 今後の拡張計画

#### Phase 2
- [ ] SNSシェア機能（Twitter/X、LINE）
- [ ] レシピ連携（外部レシピサイトへのリンク）

#### Phase 3
- [ ] AIベース推薦（Gemini API連携）
- [ ] ユーザーパターン学習
- [ ] コミュニティ機能

### 📝 ライセンス

MIT License

---

## 📖 English

### 🎯 Overview

**Kibunmeshi** is a web service that recommends the perfect food based on your current mood and delivers a comforting message.

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│   😊 Select Mood → 🍽️ Select Category → 🎲 Get Food       │
│                                                             │
│              💬 With a heartwarming message                 │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

- **Development Period**: 2 weeks (14 days)
- **Target Users**: Japanese
- **Core Function**: Mood selection + Food category selection → Food recommendation + Advice

### ✨ Key Features

| Feature | Description |
|---------|-------------|
| **😊 Mood Selection** | Choose from 6 moods (happy, sad, angry, tired, excited, calm) |
| **🍽️ Category Selection** | Korean, Western, Chinese, Japanese, Others |
| **🎲 Food Recommendation** | Random recommendation based on mood and category |
| **💬 Advice Display** | Heartwarming message tailored to the food |
| **📸 Food Images** | Beautiful food photos via Unsplash API |
| **🔄 Re-recommendation** | Get different recommendations with same criteria |

### 🛠️ Tech Stack

```
┌─────────────────────────────────────────────────────────────┐
│                        Frontend                              │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐     │
│  │  Thymeleaf  │    │   HTML5     │    │    CSS3     │     │
│  └─────────────┘    └─────────────┘    └─────────────┘     │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                         Backend                              │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐     │
│  │Spring Boot  │    │Spring       │    │  MyBatis    │     │
│  │   3.2.5      │    │ Security    │    │             │     │
│  └─────────────┘    └─────────────┘    └─────────────┘     │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                        Database                              │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐     │
│  │ PostgreSQL  │    │   MySQL     │    │  Unsplash   │     │
│  │    15       │    │   8.0       │    │    API      │     │
│  └─────────────┘    └─────────────┘    └─────────────┘     │
│                                              │                │
│                                              ▼                │
│                                    ┌─────────────┐           │
│                                    │  Gemini     │           │
│                                    │    API      │           │
│                                    └─────────────┘           │
└─────────────────────────────────────────────────────────────┘
```

| Category | Technology |
|----------|------------|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.2.5 |
| **Template** | Thymeleaf |
| **Database** | PostgreSQL 15 / MySQL 8.0 |
| **ORM** | MyBatis |
| **Security** | Spring Security |
| **Build Tool** | Gradle 8.x |
| **External API** | Unsplash (food images), Gemini API (advice generation) |

### 📊 Database Structure

- **emotions**: 6 mood types master data
- **food_categories**: 5 food category master data
- **foods**: Mood × Category combinations (90-120 items)
- **food_advice_templates**: Advice templates

### 🚀 Setup

#### Prerequisites
- Java 17+
- PostgreSQL 15+ or MySQL 8.0+
- Gradle 8.x

#### Installation Steps

1. **Clone repository**
```bash
git clone https://github.com/AN-ILGWON/Kibunmeshi.git
cd Kibunmeshi
```

2. **Create database**
```sql
-- For PostgreSQL
CREATE DATABASE kibunmeshi;

-- For MySQL
CREATE DATABASE kibunmeshi CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. **Configure application.yml**
```yaml
spring:
  datasource:
    # For PostgreSQL
    url: jdbc:postgresql://localhost:5432/kibunmeshi
    # For MySQL
    # url: jdbc:mysql://localhost:3306/kibunmeshi
    username: your_username
    password: your_password
```

4. **Get API Keys**
- Get API key from [Unsplash Developers](https://unsplash.com/developers)
- Get API key from [Google AI Studio](https://makersuite.google.com/app/apikey)
- Configure in `application.yml`

5. **Run application**
```bash
./gradlew bootRun
```

6. **Access**
- Open browser at `http://localhost:8080`

### � Screen Flow

```
┌─────────────┐
│  Home Page  │  Select mood
│ (Mood Select)│ ──────────►
└─────────────┘
                │
                ▼
┌─────────────┐
│Category Select│  Select category
│             │ ──────────►
└─────────────┘
                │
                ▼
┌─────────────┐
│ Result Page │  Food recommendation + advice
│             │  Re-recommend / Start over
└─────────────┘
```

### 🎨 Design Features

- **Mobile-first**: Responsive design for 375px-1920px
- **Responsive**: Supports mobile, tablet, and desktop
- **Colorful UI**: Different colors for each mood
- **Simple UX**: Get results in just 2 clicks

### 🔮 Future Plans

#### Phase 2
- [ ] SNS sharing (Twitter/X, LINE)
- [ ] Recipe integration (links to external recipe sites)

#### Phase 3
- [ ] AI-based recommendations (Gemini API integration)
- [ ] User pattern learning
- [ ] Community features

### 📝 License

MIT License

---

## 📖 한국어

### 🎯 서비스 개요

**키분메시**는 사용자의 현재 기분에 맞는 최적의 음식을 추천하고 마음에 위로가 되는 한 마디를 전달하는 웹 서비스입니다.

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│   😊 기분 선택 → 🍽️ 카테고리 선택 → 🎲 음식 추천           │
│                                                             │
│              💬 마음을 위로하는 한 마디와 함께               │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

- **제작 기간**: 2주일 (14일간)
- **타겟 사용자**: 일본에 거주하는 대상
- **핵심 기능**: 감정 선택 + 음식 카테고리 선택 → 음식 추천 + 조언 제공

### ✨ 주요 기능

| 기능 | 설명 |
|------|------|
| **😊 감정 선택** | 6가지 기분 (기쁨, 슬픔, 화남, 피곤, 신남, 평온) 중 선택 |
| **🍽️ 카테고리 선택** | 한식, 양식, 중식, 일식, 기타 중 선택 |
| **🎲 음식 추천** | 선택한 감정과 카테고리에 맞는 음식 랜덤 추천 |
| **💬 조언 표시** | 음식에 어울리는 마음을 위로하는 한 마디 |
| **📸 음식 이미지** | Unsplash API로 아름다운 음식 사진 표시 |
| **🔄 재추천** | 같은 조건으로 다른 음식 재추천 |

### 🛠️ 기술 스택

```
┌─────────────────────────────────────────────────────────────┐
│                        프론트엔드                            │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐     │
│  │  Thymeleaf  │    │   HTML5     │    │    CSS3     │     │
│  └─────────────┘    └─────────────┘    └─────────────┘     │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                         백엔드                                │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐     │
│  │Spring Boot  │    │Spring       │    │  MyBatis    │     │
│  │   3.2.5      │    │ Security    │    │             │     │
│  └─────────────┘    └─────────────┘    └─────────────┘     │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                        데이터베이스                           │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐     │
│  │ PostgreSQL  │    │   MySQL     │    │  Unsplash   │     │
│  │    15       │    │   8.0       │    │    API      │     │
│  └─────────────┘    └─────────────┘    └─────────────┘     │
│                                              │                │
│                                              ▼                │
│                                    ┌─────────────┐           │
│                                    │  Gemini     │           │
│                                    │    API      │           │
│                                    └─────────────┘           │
└─────────────────────────────────────────────────────────────┘
```

| 카테고리 | 기술 |
|---------|------|
| **언어** | Java 17 |
| **프레임워크** | Spring Boot 3.2.5 |
| **템플릿** | Thymeleaf |
| **데이터베이스** | PostgreSQL 15 / MySQL 8.0 |
| **ORM** | MyBatis |
| **보안** | Spring Security |
| **빌드 도구** | Gradle 8.x |
| **외부 API** | Unsplash (음식 이미지), Gemini API (조언 생성) |

### 📊 데이터베이스 구조

- **emotions**: 6가지 감정 마스터
- **food_categories**: 5가지 음식 카테고리 마스터
- **foods**: 감정×카테고리 조합 (90~120품)
- **food_advice_templates**: 조언 템플릿

### 🚀 설치 방법

#### 전제 조건
- Java 17 이상
- PostgreSQL 15 이상 또는 MySQL 8.0 이상
- Gradle 8.x

#### 설치 단계

1. **저장소 클론**
```bash
git clone https://github.com/AN-ILGWON/Kibunmeshi.git
cd Kibunmeshi
```

2. **데이터베이스 생성**
```sql
-- PostgreSQL의 경우
CREATE DATABASE kibunmeshi;

-- MySQL의 경우
CREATE DATABASE kibunmeshi CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. **application.yml 설정**
```yaml
spring:
  datasource:
    # PostgreSQL의 경우
    url: jdbc:postgresql://localhost:5432/kibunmeshi
    # MySQL의 경우
    # url: jdbc:mysql://localhost:3306/kibunmeshi
    username: your_username
    password: your_password
```

4. **API 키 발급**
- [Unsplash Developers](https://unsplash.com/developers)에서 API 키 발급
- [Google AI Studio](https://makersuite.google.com/app/apikey)에서 Gemini API 키 발급
- `application.yml`에 설정

5. **애플리케이션 실행**
```bash
./gradlew bootRun
```

6. **접속**
- 브라우저에서 `http://localhost:8080` 접속

### � 화면 흐름

```
┌─────────────┐
│  홈 화면    │  기분 선택
│ (기분 선택) │ ──────────►
└─────────────┘
                │
                ▼
┌─────────────┐
│ 카테고리 선택 │  카테고리 선택
│             │ ──────────►
└─────────────┘
                │
                ▼
┌─────────────┐
│ 결과 화면    │  음식 추천 + 조언
│             │  재추천 / 처음으로
└─────────────┘
```

### 🎨 디자인 특징

- **모바일 퍼스트**: 375px~1920px 대응
- **반응형 디자인**: 모바일, 태블릿, 데스크탑 지원
- **컬러풀한 UI**: 기분별 다른 색상 사용
- **간단한 UX**: 2클릭으로 결과 확인

### 🔮 향후 확장 계획

#### Phase 2
- [ ] SNS 공유 기능 (Twitter/X, LINE)
- [ ] 레시피 연동 (외부 레시피 사이트 링크)

#### Phase 3
- [ ] AI 기반 추천 (Gemini API 연동)
- [ ] 사용자 패턴 학습
- [ ] 커뮤니티 기능

### 📝 라이선스

MIT License
