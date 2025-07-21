# Easy Calc - simple Kotlin project

![Static Badge](https://img.shields.io/badge/1.0-%23ff0000?label=Latest%20release&labelColor=%23202124&color=%23006cd1)
![Static Badge](https://img.shields.io/badge/July%2021st%2C%202025-%23ff0000?label=Latest%20README%20edit&labelColor=%23202124&color=%23d10076)
![Static Badge](https://img.shields.io/badge/Bartosz%20Str%C4%85czek-%23ff0000?style=social&logo=GitHub&label=Main%20developer&labelColor=%23202124&color=%23d10076)

![Static Badge](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=Android&logoColor=white)
![Static Badge](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=Kotlin&logoColor=white)

## Links

![portfolio](https://img.shields.io/badge/my_portfolio-000?style=for-the-badge&logo=ko-fi&logoColor=white)
![Static Badge](https://img.shields.io/badge/Stack%20Overflow-%23ffffff?style=for-the-badge&logo=Stack%20Overflow&logoColor=%23ffffff&color=%23F58025)
[![Static Badge](https://img.shields.io/badge/Hackerrank-%23ffffff?style=for-the-badge&logo=Hackerrank&logoColor=%23ffffff&color=%2300EA64)](https://www.hackerrank.com/profile/bartoszstraczek1)
![Static Badge](https://img.shields.io/badge/LeetCode-%23ffffff?style=for-the-badge&logo=LeetCode&logoColor=%23ffffff&color=%23FFA116)
![Static Badge](https://img.shields.io/badge/Gmail-%23ffffff?style=for-the-badge&logo=Gmail&logoColor=%23ffffff&color=%23EA4335)
![Static Badge](https://img.shields.io/badge/Github-%23ffffff?style=for-the-badge&logo=Github&logoColor=%23ffffff&color=%23181717)

*If you want to support my work, you can buy me a coffee:*

[!["Buy Me A Coffee"](https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png)](https://buymeacoffee.com/nkbdev)

## About

**Easy Calc** is a simple, minimalistic application that resembles a standard calculator we're all familiar with. The goal of creating such an app was to learn basics of Kotlin because I know Java to some extend, as I used to create plugins for Minecraft servers, but I didn't implement it for bigger project. That's why I decided to learn Kotlin, as it's a preferred language by Google, as less and less apps are being developed in Java.

I wrote in the title that this app is simple, yet I spent quite a lot hours on it, while it took me only a week to develop. That's because I solely focused on this branch, as I think mobile apps are more used that the desktop ones.

## Features

- Adding, substracting, multiplying and dividing
- Calculating sqrt() of a number during calculations
- Keeping track of the history of calculations
- Possibility to load calculations done during a session
- Positive/negative numbers included
- Numbers are rounded 10 digits to the point

## Screenshots
<div align=center>
  <img width="240" alt="image" src="https://github.com/user-attachments/assets/e571e4b6-942a-4d6d-a8bb-852839ba8e79" />
  &nbsp;
  <img width="240" alt="image" src="https://github.com/user-attachments/assets/21ffe1b2-ed93-4d97-a93f-cff25afa3e1c" />
  &nbsp;
  <img width="240" alt="image" src="https://github.com/user-attachments/assets/140817da-f5d2-4b52-83fa-76a131f98dfd" />
  &nbsp;
  <img width="240" alt="image" src="https://github.com/user-attachments/assets/1dca7e26-64c4-4a4d-8e50-ca43ded0c602" />
  &nbsp;
  <img width="240" alt="image" src="https://github.com/user-attachments/assets/130423c8-d190-4102-a662-f054970fd848" />
  &nbsp;
  <img width="240" alt="image" src="https://github.com/user-attachments/assets/13d18bb7-c70a-4df1-808e-dedbb37e8dfe" />
  &nbsp;
  <img width="240" alt="image" src="https://github.com/user-attachments/assets/6d2022f4-50a9-4d96-b07a-08048dd0a6a7" />
  &nbsp;
  <img width="240" alt="image" src="https://github.com/user-attachments/assets/d643774d-6fa9-44e5-bd72-3354d913302c" />
  &nbsp;
  <img width="240" alt="image" src="https://github.com/user-attachments/assets/5967a481-2a70-43eb-a8a4-52543964603c" />
  &nbsp;
</div>

## What I learned

Here's an example code to create an extension method. I had to fix the problem where the result of `2.3 - 1.7` would be `0.5999999999...`, so I fixed it with those lines of code:

```kotlin
fun Double.smartFormat(decimals: Int = 10): String {
    val rounded = "%.${decimals}f".format(this).toDouble()
    val final = if (kotlin.math.abs(this - rounded) < 1e-9)
        rounded
    else this
    return if (final % 1 == 0.0) final.toInt().toString() else final.toString()
}
```

Also I wasn't a fan of using interfaces, but as the MainActivity.kt is loaded first, I can add an interface to the class, and add it to the list of listeners. If something occurs, I notify all the listeners, and then the action is performed elsewhere. Here's an example:

```kotlin
interface CardListener {
    fun onCardClicked(co: CalcOperation)
    fun onHistoryCleared(msg: String)
}
```

And how it can be implemented:

```kotlin
override fun onCardClicked(co: CalcOperation) {
    opLbl.text = "${co.first.smartFormat()} ${Customizable.getSymbol(co.op)}" +
            " ${co.second.smartFormat()} ="
    resultText.text = co.result.smartFormat()
    inputType = Input.EQUALS
}

override fun onHistoryCleared(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
```


