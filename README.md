# 概要

SpringBoot2.5を利用したRestAPIの開発サンプルとして、TODOのCRUDを出来るようにしたサンプルプロジェクトです。
これさえあれば、RestAPIの開発、JUnitでのテスト、開発環境とステージング環境ごとの環境依存設定の切り替え、などなど、
システム開発を請け負うシステム屋さんとして最低限の非機能要件部分を盛り込んだものにしました。

# Eclipseの実行構成

## SpringBootをローカル開発環境で実行する構成

SpringBootでrestcontroller-sampleを実行する際、実行の構成でプログラムの引数に以下定義を指定して実行する。

```
--spring.profiles.active=local
```

## Mavenでテスト実行する際のゴール指定

- テストを実行
- テストコードのカバレッジレポートを出力（JaCoCo）
- テストレポートを出力（maven-surefire-report-plugin）

```
clean test jacoco:report site surefire-report:report
```
