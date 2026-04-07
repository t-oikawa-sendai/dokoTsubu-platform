# Eclipse2025環境における DokoTsubu 起動障害 原因・対策記録

## 最終結論（先に結論）
今回の障害の主因は、dokoTsubu-platform（DokoTsubu2）の Java Build Path に Tomcat Server Runtime が正しく入っておらず、Servlet API を解決できない状態だったことである。  
その結果、Tomcat10_Java21 起動時に NoClassDefFoundError: HttpServletRequest / ClassNotFoundException: HttpServletRequest が発生した。  
Register.java の import 自体は jakarta.servlet.* で正しく、ソース記述ミスではなかった。  
Server Runtime [Tomcat10 (Java21)] を Build Path に明示追加後、Tomcat10_Java21 は正常起動し、ブラウザ表示まで確認できた。

## 対象環境
- **作業端末**: MacBook Air
- **対象IDE**: `/Applications/Eclipse_2025-12.app`
- **対象ワークスペース**: `/Applications/Eclipse_2025-12.app/Contents/workspace`
- **対象プロジェクト**: `/Users/takashioikawa/Dev/dokoTsubu-platform/DokoTsubu2`
- **比較対象としての正常環境**: **Eclipse_2026-03 側は今回触っていない**（正常環境の設定変更・混在はしていない）

## 確認できた事実（Fact）
以下はログ・設定・動作確認により**確認できた事実**である。

- Tomcat10_Java21 で `dokoTsubu-platform`（DokoTsubu2）を起動した際、最初の本質エラーは `NoClassDefFoundError: HttpServletRequest` / `ClassNotFoundException: HttpServletRequest` だった。
- `Register.java` の source import は `jakarta.servlet.*` で正しかった。
- Java Build Path の Libraries 一覧に、当初 Server Runtime が見えていなかった。
- Server Runtime `[Tomcat10 (Java21)]` を Java Build Path に明示追加後、Tomcat10_Java21 は正常起動し、ブラウザ表示まで確認できた。

## 判断・推定（Inference）
以下は、上記 Fact に基づく**判断・推定**である（事実とは区別する）。

- 問題の中心はソースの import 記述ミスではなく、Eclipse プロジェクト設定（Java Build Path）側の不整合である可能性が高い。
- Server Runtime 不足により Servlet API が解決できない状態となり、Web アプリの起動処理（アノテーション解析等）で `HttpServletRequest` を解決できずに失敗したと考えられる。

## 発生原因（Cause / Root Cause）
### 原因の概要
- `dokoTsubu-platform`（DokoTsubu2）の **Java Build Path に Tomcat Server Runtime が正しく反映されていなかった**。
- その結果、Servlet API（`HttpServletRequest` 等）を解決できず、起動時に `NoClassDefFoundError / ClassNotFoundException` が発生した。

### 影響範囲（整理）
- **IDE/実行基盤層（Eclipse/Build Path）**: Server Runtime 未反映により、Servlet API 解決が破綻する。
- **アプリコード層**: `Register.java` の import は `jakarta.servlet.*` で正しく、本件の直接原因ではない。

## 対策内容（Fix）
### 対策手順（Eclipse プロジェクト設定）
- `dokoTsubu-platform`（DokoTsubu2）のプロパティで Targeted Runtime を `Tomcat10 (Java21)` に設定。
- Java Build Path の Libraries に Server Runtime `[Tomcat10 (Java21)]` を追加。

### 対策手順（Tomcat 再起動）
- 設定反映後、`Tomcat10_Java21` を再起動（再公開を含む）。

### 対策結果（確認）
- `HttpServletRequest` の `NoClassDefFoundError / ClassNotFoundException` は消滅。
- Tomcat 起動成功。
- ブラウザ表示成功。
- 本件は Tomcat 本体起動障害ではなく、Webアプリ配備時の Servlet API 解決失敗であった。

## 今回わかったこと（学び）
- `NoClassDefFoundError / ClassNotFoundException` は、ソース不良と即断せず **Build Path 不足**を最優先で切り分けるべきである。
- Eclipse 側で Runtime 定義が存在するだけでは不十分で、**プロジェクトの Java Build Path に Server Runtime が入っているか**が実害に直結する。

## 再発防止策（Preventive Actions）
- 起動障害時は、まずエラーメッセージから **クラス解決失敗（Build Path）**か **Tomcat 本体起動失敗**かを分離する。
- `NoClassDefFoundError / ClassNotFoundException` が出た場合、対象クラスについて **Build Path 不足**を最優先確認項目に入れる。
- 正常環境と障害切り分け環境を混在させない（設定差分が追えなくなるため）。

