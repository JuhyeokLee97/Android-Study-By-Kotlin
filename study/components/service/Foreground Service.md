# Foreground Service
**Foreground Service**에 대해서 안드로이드 공식 개발 문서에서는 다음과 같이 설명한다.

> Foreground services perform operations that are noticeable to the user.
>
> Foreground services show a **status bar notification**, so that users are actively aware that your app is performing a task in the foreground and is consuming system resouces.
> 
> Devices that run Android 12(API level 12) or higher provide a streamlined experience  for short-running foreground services.
on these devices, the system wait 10 seconds before showing the notification associated with a foreground service.
There are a few exception; several types of services always **display a notification immediately**.
>
> Examples of apps that would use foreground services include the following:
>
> - A music player app that plays music in a foreground service. The notification might show the current song that is being played.
> - A fitness app that records a user's run in a foreground service, after receiving permission form the user. The notification might show the distance that the user has traveled during the current fitness session.
>
> You should only use a foreground service when you app needs to perform a task that is noticeable by the user even when they're not directly interacting with the app.
if the action is of low enough importance that you want to use a minimum-priority notification, create a **background task** instead.

내가 이해한 바로는 **Foreground Service**는 사용자에게 알림을 통해 알릴 정보가 있는 경우 사용하는 컴포넌트이다.

예를 들면 뮤직 플레이 앱에서 아래와 같은 **Foreground Service**를 제공한다.
<img src="https://www.xda-developers.com/files/2022/03/Screenshot_20220317-15283.jpg" height=550/>
