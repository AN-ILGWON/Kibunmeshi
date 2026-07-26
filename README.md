# きぶんめし (Kibunmeshi)

> 今日の気分を選ぶだけで、ぴったりのごはんと心に寄り添う一言をお届けするWebサービス

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 📖 日本語

### 🎯 サービス概要

**きぶんめし**は、ユーザーの現在の気分に合わせて最適な料理を推薦し、心に寄り添う一言を届けるWebサービスです。

- **開発期間**: 2週間（14日間）
- **ターゲットユーザー**: 日本在住の20〜30代
- **コア機能**: 気分選択 + 料理カテゴリ選択 → 料理推薦 + アドバイス提供

### ✨ 主な機能

#### MVP機能（必須）
- **😊 気分選択**: 6種類の気分（うれしい、かなしい、おこ、つかれた、わくわく、おだやか）から選択
- **🍽️ 料理カテゴリ選択**: 韓国料理、洋食、中華、和食、その他から選択
- **🎲 料理推薦**: 選択した気分とカテゴリに合った料理をランダム推薦
- **💬 アドバイス表示**: 料理に合わせた心に寄り添う一言を表示
- **📸 料理画像表示**: Unsplash APIで美しい料理写真を表示
- **🔄 再推薦機能**: 同じ条件で別の料理を再推薦

#### 拡張機能（オプション）
- 推薦履歴の保存・参照
- お気に入り機能
- ユーザーログイン

### 🛠️ 技術スタック

| カテゴリ | 技術 |
|---------|------|
| **バックエンド** | Spring Boot 3.x, Spring Security, MyBatis |
| **フロントエンド** | Thymeleaf, HTML5, CSS3 |
| **データベース** | MySQL 8.0 |
| **外部API** | Unsplash（料理画像） |
| **ビルドツール** | Gradle |
| **言語** | Java 17 |

### 🏗️ アーキテクチャ

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   Browser   │◄──►│Spring Boot  │◄──►│   MySQL     │
│  (Thymeleaf)│    │  Controller │    │  Database   │
└─────────────┘    └─────────────┘    └─────────────┘
                        │
                        ▼
                   ┌─────────────┐
                   │  Unsplash   │
                   │    API      │
                   └─────────────┘
```

### 📊 データベース設計

- **emotions**: 6種類の気分マスター
- **food_categories**: 5種類の料理カテゴリマスター
- **foods**: 気分×カテゴリの組み合わせ（90〜120品）
- **food_advice_templates**: アドバイステンプレート

詳細なDB設計は [`docs/4_kibunmeshi-database-spec.md`](./docs/4_kibunmeshi-database-spec.md) を参照してください。

### 🚀 セットアップ

#### 前提条件
- Java 17以上
- MySQL 8.0以上
- Gradle 8.x

#### 手順

1. **リポジトリのクローン**
```bash
git clone https://github.com/AN-ILGWON/Kibunmeshi.git
cd Kibunmeshi
```

2. **データベースの設定**
```sql
CREATE DATABASE kibunmeshi;
```

3. **application.ymlの設定**
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/kibunmeshi
    username: your_username
    password: your_password
```

4. **Unsplash APIキーの取得**
- [Unsplash Developers](https://unsplash.com/developers) でAPIキーを取得
- `application.yml` に設定

5. **アプリケーションの起動**
```bash
./gradlew bootRun
```

6. **アクセス**
- ブラウザで `http://localhost:8080` にアクセス

### 📱 画面遷移

```
トップ（気分選択）
    ↓
カテゴリ選択
    ↓
結果表示（料理推薦 + アドバイス）
    ↓
再推薦 / 最初から
```

詳細な画面仕様は [`docs/3_kibunmeshi-screen-spec.md`](./docs/3_kibunmeshi-screen-spec.md) を参照してください。

### 📄 プロジェクトドキュメント

- [要件定義書](./docs/2_kibunmeshi-requirements.md) - 機能要件、非機能要件
- [画面定義書](./docs/3_kibunmeshi-screen-spec.md) - 画面仕様、UIコンポーネント
- [データベース設計書](./docs/4_kibunmeshi-database-spec.md) - ER図、テーブル定義
- [開発ログ](./docs/6_kibunmeshi-development-log.md) - 開発日記
- [環境設定ガイド](./docs/6_환경설정가이드.md) - セットアップ手順

### 🎨 デザイン

- **モバイルファースト**: 375px〜1920pxに対応
- **レスポンシブデザイン**: モバイル、タブレット、デスクトップに対応
- **カラフルなUI**: 気分ごとに異なる色を使用
- **シンプルなUX**: 2クリックで結果確認

### 🔮 今後の拡張計画

#### Phase 2（3〜4週目予定）
- [ ] SNSシェア機能（Twitter/X、LINE）
- [ ] 運勢連携（今日の運勢と料理マッチング）
- [ ] レシピ連携（外部レシピサイトへのリンク）

#### Phase 3（2ヶ月後）
- [ ] AIベース推薦（Gemini API連携）
- [ ] ユーザーパターン学習
- [ ] コミュニティ機能



---

## 📖 English

### 🎯 Overview

**Kibunmeshi** is a web service that recommends the perfect food based on your current mood and delivers a comforting message.

- **Development Period**: 2 weeks (14 days)
- **Target Users**: Japanese residents aged 20-30
- **Core Function**: Mood selection + Food category selection → Food recommendation + Advice

### ✨ Key Features

#### MVP Features (Essential)
- **😊 Mood Selection**: Choose from 6 moods (happy, sad, angry, tired, excited, calm)
- **🍽️ Food Category Selection**: Korean, Western, Chinese, Japanese, Others
- **🎲 Food Recommendation**: Random recommendation based on mood and category
- **💬 Advice Display**: Heartwarming message tailored to the food
- **📸 Food Image Display**: Beautiful food photos via Unsplash API
- **🔄 Re-recommendation**: Get different recommendations with same criteria

#### Extended Features (Optional)
- Recommendation history save/view
- Favorites functionality
- User login

### 🛠️ Tech Stack

| Category | Technology |
|---------|-----------|
| **Backend** | Spring Boot 3.x, Spring Security, MyBatis |
| **Frontend** | Thymeleaf, HTML5, CSS3 |
| **Database** | MySQL 8.0 |
| **External API** | Unsplash (food images) |
| **Build Tool** | Gradle |
| **Language** | Java 17 |

### 🚀 Setup

#### Prerequisites
- Java 17+
- MySQL 8.0+
- Gradle 8.x

#### Steps

1. **Clone repository**
```bash
git clone https://github.com/AN-ILGWON/Kibunmeshi.git
cd Kibunmeshi
```

2. **Database setup**
```sql
CREATE DATABASE kibunmeshi;
```

3. **Configure application.yml**
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/kibunmeshi
    username: your_username
    password: your_password
```

4. **Get Unsplash API Key**
- Get API key from [Unsplash Developers](https://unsplash.com/developers)
- Configure in `application.yml`

5. **Run application**
```bash
./gradlew bootRun
```

6. **Access**
- Open browser at `http://localhost:8080`

### 📄 Project Documentation


- [Requirements Specification](./docs/2_kibunmeshi-requirements.md) - Functional/non-functional requirements
- [Screen Specification](./docs/3_kibunmeshi-screen-spec.md) - Screen specs, UI components
- [Database Design](./docs/4_kibunmeshi-database-spec.md) - ER diagram, table definitions
- [Development Log](./docs/6_kibunmeshi-development-log.md) - Development diary
- [Environment Setup Guide](./docs/6_환경설정가이드.md) - Setup instructions





---

## 📖 한국어

### 🎯 서비스 개요

**키분메시**는 사용자의 현재 기분에 맞는 최적의 음식을 추천하고 마음에 위로가 되는 한 마디를 전달하는 웹 서비스입니다.

- **제작 기간**: 2주일 (14일간)
- **타겟 사용자**: 일본에 거주하는 20~30대
- **핵심 기능**: 감정 선택 + 음식 카테고리 선택 → 음식 추천 + 조언 제공

### ✨ 주요 기능

#### MVP 기능 (필수)
- **😊 감정 선택**: 6가지 기분 (기쁨, 슬픔, 화남, 피곤, 신남, 평온) 중 선택
- **🍽️ 음식 카테고리 선택**: 한식, 양식, 중식, 일식, 기타 중 선택
- **🎲 음식 추천**: 선택한 감정과 카테고리에 맞는 음식 랜덤 추천
- **💬 조언 표시**: 음식에 어울리는 마음을 위로하는 한 마디
- **📸 음식 이미지 표시**: Unsplash API로 아름다운 음식 사진 표시
- **🔄 재추천 기능**: 같은 조건으로 다른 음식 재추천

#### 확장 기능 (옵션)
- 추천 히스토리 저장·조회
- 즐겨찾기 기능
- 사용자 로그인

### 🛠️ 기술 스택

| 카테고리 | 기술 |
|---------|------|
| **백엔드** | Spring Boot 3.x, Spring Security, MyBatis |
| **프론트엔드** | Thymeleaf, HTML5, CSS3 |
| **데이터베이스** | MySQL 8.0 |
| **외부 API** | Unsplash (음식 이미지) |
| **빌드 도구** | Gradle |
| **언어** | Java 17 |

### 🚀 설치 방법

#### 전제 조건
- Java 17 이상
- MySQL 8.0 이상
- Gradle 8.x

#### 단계

1. **저장소 클론**
```bash
git clone https://github.com/AN-ILGWON/Kibunmeshi.git
cd Kibunmeshi
```

2. **데이터베이스 설정**
```sql
CREATE DATABASE kibunmeshi;
```

3. **application.yml 설정**
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/kibunmeshi
    username: your_username
    password: your_password
```

4. **Unsplash API 키 발급**
- [Unsplash Developers](https://unsplash.com/developers)에서 API 키 발급
- `application.yml`에 설정

5. **애플리케이션 실행**
```bash
./gradlew bootRun
```

6. **접속**
- 브라우저에서 `http://localhost:8080` 접속

### 📄 프로젝트 문서


- [요구사항 정의서](./docs/2_kibunmeshi-requirements.md) - 기능 요구사항, 비기능 요구사항
- [화면 정의서](./docs/3_kibunmeshi-screen-spec.md) - 화면 설계, UI 컴포넌트
- [데이터베이스 설계서](./docs/4_kibunmeshi-database-spec.md) - ER 다이어그램, 테이블 정의
- [개발 로그](./docs/6_kibunmeshi-development-log.md) - 개발 일지
- [환경설정 가이드](./docs/6_환경설정가이드.md) - 설치 안내


