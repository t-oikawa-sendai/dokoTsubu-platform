# Eclipse2025環境における DokoTsubu コンテキスト衝突 原因・対策記録

## 最終結論（先に結論）
今回の問題は、旧 `DokoTsubu` と新 `DokoTsubu2` を同一 Eclipse2025 ワークスペース内で共存させた際に、Tomcat 上で **`/dokoTsubu` のコンテキストが衝突し得る状態**になっていたことである。  
新 `DokoTsubu2` 側の WTP 設定 `.settings/org.eclipse.wst.common.component` において、`context-root` / `deploy-name` が `dokoTsubu` のままだったため、旧 `DokoTsubu` と同一コンテキストとして公開され得た。  
対策として、`DokoTsubu2` 側の `context-root` / `deploy-name` を `dokoTsubu2` に変更し、Tomcat 上のコンテキストパス衝突を回避する。

## 対象環境
- **作業端末**: MacBook Air
- **対象IDE**: `/Applications/Eclipse_2025-12.app`
- **対象ワークスペース**: `/Applications/Eclipse_2025-12.app/Contents/workspace`
- **対象プロジェクト**: `/Users/takashioikawa/Dev/dokoTsubu-platform/DokoTsubu2`
- **比較対象としての正常環境**: **Eclipse_2026-03 側は今回触っていない**（正常環境の設定変更・混在はしていない）

## 確認できた事実（Fact）
以下は設定ファイル確認により**確認できた事実**である。

- 新プロジェクト `DokoTsubu2` の WTP 設定ファイル `.settings/org.eclipse.wst.common.component` に `context-root` と `deploy-name` の定義がある。
- 変更前は `context-root` が `dokoTsubu`（= `/dokoTsubu`）で、旧 `DokoTsubu` と同一コンテキストになり得る状態だった。
- 変更前は `deploy-name` も `dokoTsubu` で、公開名としても衝突し得る状態だった。
- 変更後は `context-root` を `dokoTsubu2`、`deploy-name` を `dokoTsubu2` に更新した。

## 判断・推定（Inference）
以下は、上記 Fact に基づく**判断・推定**である（事実とは区別する）。

- 同一 Tomcat 上に旧 `DokoTsubu` と新 `DokoTsubu2` を同時に載せる場合、`context-root` が同一だとコンテキスト衝突が発生し得る。
- `DokoTsubu2` 側の `context-root` / `deploy-name` を分離することで、Tomcat 上のコンテキストパス衝突を回避できる。

## 発生原因（Cause / Root Cause）
### 原因の概要
- `DokoTsubu2` の WTP 設定 `.settings/org.eclipse.wst.common.component` において、`context-root` / `deploy-name` が `dokoTsubu` のままだった。
- その結果、旧 `DokoTsubu` と同一の `/dokoTsubu` として公開され得る状態となり、同一 Tomcat 上でコンテキスト衝突が起こりうる。

### 影響範囲（整理）
- **IDE/実行基盤層（Eclipse/WTP）**: `context-root` / `deploy-name` により、Tomcat への公開先（コンテキスト）が決まる。
- **アプリコード層**: 本件（衝突回避）の対策自体は WTP 設定変更であり、Java/JSP の修正は含まない（未対応事項は後述）。

## 対策内容（Fix）
### 対策手順（WTP 設定）
- `DokoTsubu2` の `.settings/org.eclipse.wst.common.component` を修正し、`context-root` を `dokoTsubu` → `dokoTsubu2` に変更。
- 同ファイルで `deploy-name` を `dokoTsubu` → `dokoTsubu2` に変更。

### 対策結果（確認）
- 同一 Tomcat 上で、WTP 設定上のコンテキストパス衝突（`/dokoTsubu` の二重公開）を回避できる状態になる。

## 未対応事項（今回の修正範囲外）
Java/JSP 側には `/dokoTsubu` を前提とするハードコードが残っているため、**今回の修正だけでは `/dokoTsubu2` での完全動作は未保証**である。  
今回の修正で確定するのは、あくまで **WTP 設定上のコンテキスト衝突回避**までである。

## 修正の期待結果
- 旧 `DokoTsubu` は `/dokoTsubu` で起動可能
- 新 `DokoTsubu2` は WTP 設定上 `/dokoTsubu2` として公開可能
- 同一 Tomcat 上でコンテキストパス衝突を回避できる
- ただし `/dokoTsubu2` での完全動作は別途確認が必要

