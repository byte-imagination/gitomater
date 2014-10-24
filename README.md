# Gitomater - gitolite-admin gradle/java wrapper

Nice tool to manage git repositories handled by gitolite.

## Table of Contents

* [Gradle](#gradle)
    * [Configuration](#configuration)
    * [Usage](#usage)

## Gradle

### Configuration

All you have to do is to define the path to your git server. Just insert this line: 
```
gitomaterGitoliteRepository=ssh://user@server/gitolite-admin.git
```
into your ~/.gradle/gradle.properties.

### Usage

First, you need to:

- initialize

```bash
gradle init
```