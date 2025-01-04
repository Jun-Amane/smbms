# SMBMS memo

## 关于git

### 关键信息
- 仓库地址：git@github.com:Jun-Amane/smbms.git

- 尽量使用ssh协议。
  * `git@github.com:Jun-Amane/smbms.git`
  * 而非HTTPS协议`https://github.com/Jun-Amane/smbms.git`
  * ssh协议需要提前配置好public key，在本地生成密钥对，并在GitHub账号中配置公钥。
  * ssh的协议的好处是防止DNS污染，并且能够免密码操作（使用密钥）


- 设置好自己的用户名与邮箱（与GitHub账户对应），例如：
    * `git config --global user.name "Jun Amane"`
    * `git config --global user.email "Jun.Amane@zzy040330.moe"`
    * 保证GitHub上能够正确记录提交人。


### 注意事项

- **不要**使用`--force`推送：会覆盖别人的提交信息
- 避免使用`git rebase`：改变提交历史来整合更改，这可能会导致其他开发人员在使用相同分支时出现冲突。建议在自己的feature分支上使用rebase之前，确保没有其他人在使用。此外，永远不要在公共或共享分支上rebase。

### 分支设置

- master: 主分支，用于稳定的代码和发布版本
- dev：开发分支，用于集成新的功能和修复
- feature/xxx：个人本地开发使用
    * 分支命名遵循格式 `feature/<your-username>`，例如 `feature/Jun-Amane`
    * 主要在本地开发使用
    * 需要使用Pull Request时可以提交到远程仓库，但一定要设置正确的分支名，避免冲突。

### 流程（按需调整，仅供参考）

- ***以下代码都是示例，演示操作步骤！请勿直接盲目使用！***

- **克隆仓库**
   ```bash
   git clone git@github.com:Jun-Amane/smbms.git
   ```

- **切换到开发分支**
   ```bash
   git checkout dev
   ```

- **创建个人功能分支**
   ```bash
   git checkout -b feature/<your-username>
   ```

- **在功能分支进行开发和提交**
    * 修改代码并提交：
    ```bash
    git add .
    git commit -m "feat: some conventional commit messages"
    ```

- **推送更改到`dev`分支**
    ```bash
    git checkout dev        # 切换到dev分支
    git pull origin dev     # 从远程拉取dev分支，更新代码
    git merge feature/<your-username> # 将本地分支feature/your-username合并到dev
    ```

    * 如果在合并时发生冲突，手动编辑冲突文件并解决冲突后：
    ```bash
    git add <resolved-files>
    git commit
    ```

    * 推送到远程`dev`分支：
    ```bash
    git push origin dev
    ```

- **遇到关键冲突时，使用Pull Request进行合并**
    * 当直接推送导致冲突无法自动解决，或对于较大或重要的更新，建议：
        + 将功能分支推送到远程：
        ```bash
        git push origin feature/<your-username>
        ```
        + 在GitHub上创建Pull Request，将功能分支合并到`dev`分支。

- **维护清理**
    * 删除本地和远程的功能分支（如不再需要）：
     ```bash
     git branch -d feature/<your-username>
     git push origin --delete feature/<your-username>
     ```
    * 删除操作无法回滚，需谨慎
    * 删除前确保代码已经合并或不再使用
    * ***不要删除master或dev以及别人的分支！***

### 关于提交信息（commit message）的撰写

- ***用英文！***
    * 需要用到中文字符时，使用UTF-8，而不是GBK！

- 在提交代码时，需要按照[Conventional Commits](https://www.conventionalcommits.org/en)规范撰写commit message（建议先阅读一下链接里的内容）。这有助于保持提交历史的整洁和可读性，同时对自动化版本控制工具也更友好。

- 以下是基本格式：
```
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

- 对于简单的提交，不需要body和footers，使用简短的文字描述提交的更改，例如
    - 添加了User类（JavaBean）
    ```plaintext
    feat(entity): add User class as a JavaBean
    ```

    - 实现了UserMapper类
    ```plaintext
    feat(mapper): implement UserMapper class
    ```

    - 添加了密码加密的功能
    ```plaintext
    feat: add password encryption feature
    ```

    - 修复了无法自动重定向的bug
    ```plaintext
    fix(view): resolve auto-redirect issue
    ```

    - 调整了代码格式
    ```plaintext
    style: adjust code formatting for consistency
    ```

    - 更新贡献指南
    ```plaintext
    doc: update the contributing-guidelines
    ```

### 提交原则：一次提交只做一件事

- 每个提交应该只包含***一个独立的变更***。这有助于轻松追踪更改历史，提高代码审查的效率，并简化回滚操作。

#### 示例

假设正在实现多个JavaBean类，比如`User`和`Product`类。应将它们放在单独的提交中，而不是在一个提交中同时实现。

- 实现User类的提交
  ```plaintext
  feat(entity): add User class as a JavaBean
  ```

- 实现Product类的提交
  ```plaintext
  feat(entity): add Product class as a JavaBean
  ```

### 目录组织

```bash
.
├── backend     # 后端
├── database    # 数据库
├── doc         # 文档
│   └── memo.md
├── frontend    # 前端
├── LICENSE     # 开源许可
├── README.md
├── scripts     # 脚本
└── utils       # 实用工具
```

## 开发规范

- ***使用UTF-8！***
- ***变量、函数、类名等命名使用英文，并遵循所使用编程语言中的命名规则（例如Java中的驼峰命名法）。***
- 尽量用可用的设计模式
- 保持代码的自动格式化，使用IDE或工具提供的格式化功能，保持缩进、空格等风格的一致。
- 在关键位置添加注释，**特别是在可能造成困惑或需要解释的地方**，尽量**使用英文**注释。
- 使用**JavaDoc**风格的注释，为类、方法和重要逻辑块提供明确的描述。（IntelliJ IDEA可以自动生成）
- 文件头
    * 示例
    ```java
    /**
     * Package: moe.zzy040330.smbms.entity
     * File: User.java
     * Author: Ziyu ZHOU
     * Date: 03/01/2024
     * Time: 12:34
     * Description: This class represents a User entity with basic information. 
     * It adheres to the JavaBean conventions, providing getter and setter methods for
     * accessing and modifying these properties.
     */
    ```
    * Intellij IDEA中可以自动生成，添加File Template模板
    ```java
    /**
     * Package: ${PACKAGE_NAME}
     * File: ${NAME}.java
     * Author: Ziyu ZHOU
     * Date: ${DAY}/${MONTH}/${YEAR}
     * Time: ${TIME}
     * Description: TODO: change me
     */
    ```

---

# 技术栈与版本
- 前端
    * Next.js (最新稳定版)
    * React (最新稳定版)
    * Typescript 
- 后端
    * Spring Boot (最新稳定版)
    * Java (OpenJDK 21)
    * Maven (最新稳定版)
    * Tomcat 11
- 数据库
    * MariaDB (MySQL)


---

rev. 0401 by Jun.


