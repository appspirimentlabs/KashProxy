# KashProxy
 ![Chucke Version](https://img.shields.io/maven-central/v/com.github.chuckerteam.chucker/library?label=Chucker)  ![License](https://img.shields.io/github/license/arunkarshan/KashProxy.svg?color=orange)  [![PRs Welcome](https://img.shields.io/badge/PRs-welcome-orange.svg)](http://makeapullrequest.com)

<br/><br/>
<p align="center">
  <img src="assets/ic_launcher_web.png" alt="chucker icon" width="40%"/>
</p>

<br/>

* [Acknowledgments](#acknowledgments-)
* [License](#license-)

KashProxy is born from a thought to have the Charles Proxy within the application to make life of developers and testers the testing easier.

KashProxy simplifies the process of mocking of **HTTP(S) requests/responses** fired by your Android App. KashProxy works as an **OkHttp Interceptor**, and will provide the required response for the URLs you configured in the KashProxy Mapping List. It provides the required UI for create mapping for each API URL.

KashProxy also integrate the famous Network Monitoring library [Chucker](https://github.com/ChuckerTeam/chucker) in it, in order to make the mapping of URL more easier and effortless. The app will bring all the features of [Chucker](https://github.com/ChuckerTeam/chucker) too, providing easy extension functions to set up [Chucker](https://github.com/ChuckerTeam/chucker) and KashProxy.

Apps using KashProxy will display a **notification** showing a summary of ongoing HTTP response mappings. Tapping on the notification launches the full KashProxy UI. Apps can optionally suppress the notification, and launch the KashProxy UI and [Chucker](https://github.com/ChuckerTeam/chucker) directly from within their own interface.


## Acknowledgments 🌸

### Maintainers

KashProxy is currently developed and maintained by the [Arun Shankar](https://github.com/arunkarshan). When submitting a new PR, please ping one of:

- [@arunkarshan](https://github.com/arunkarshan)


### Thanks

Big thanks to the Chucker Team ❤️

### Libraries

KashProxy uses the following open source libraries:

- [Chucker](https://github.com/ChuckerTeam/chucker) - Copyright 2018-2021 Chucker Team.
- [OkHttp](https://github.com/square/okhttp) - Copyright Square, Inc.
- [Gson](https://github.com/google/gson) - Copyright Google Inc.
- [Room](https://developer.android.com/topic/libraries/architecture/room) - Copyright Google Inc.

## License 📄

```
    Copyright (C) 2022 Arun Shankar.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
```
