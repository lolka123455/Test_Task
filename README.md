
# About the project
This is a test assignment for a company, I got the job, but I decided to finish it.

In brief, the application consists of several modules. One screen - one module.
Stack - MVVM, Coroutine, Room, Retrofit, Koin, Glide, Google Navigation

At initial start data is loaded from network and cached, further all data is taken from cache

When the download speed is slow then instead of a boring progress bar was used library from facebook - shimmer

Download apk (choose the latest version or the one you want) - https://github.com/lolka123455/Test_Task/releases

Figma - https://www.figma.com/file/ZB11LnTeIQtlMys9NoZfP3/test5?t=qlTectA9AShr0KlQ-0

The API consists of three Get requests: 
- Load basic information
- Load detailed information about phone
- Load cart info






## Build and run

First the project was written on android studio Dolphin 2021.3.1 Patch 1 then there was a switch to Electric Eel 2022.1.1

Gradle version 7.3.1

SDK version

-min 21

-target 33
## API Reference

#### Main screen

```
  GET https://run.mocky.io/v3/654bd15e-b121-49ba-a588-960956b15175
```

- Hot Sales

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `Int` | **Required**. Id of item |
| `is_new` | `Boolean` | **Required**. Whether item is new or not |
| `title` | `String` | **Required**. Title of item |
| `subtitle` | `String` | **Required**. Subtitle of item|
| `picture` | `String` | **Required**. URL of the item's picture |
| `is_buy` | `Boolean` | **Required**. Whether item is buyable or not |

- Best Seller

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `Int` | **Required**. Id of item |
| `is_favorites` | `Boolean` | **Required**. Whether item is in favorites or not |
| `title` | `String` | **Required**. Title of item |
| `price_without_discount` | `Int` | **Required**. Price of item without discount |
| `discount_price` | `Int` | **Required**. Price of item with discount |
| `picture` | `String` | **Required**. URL of the item's picture |

#### Detail screen

```
  GET https://run.mocky.io/v3/6c14c560-15c6-4248-b9d2-b4508df7d4f5
``` 

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Unique id of the item |
| `title` | `String` | **Required**. Title of item |
| `price` | `Int` | **Required**. Price of the item|
| `rating` | `Double` | **Required**. Rating of the item |
| `isFavorites` | `Boolean` | **Required**. sFavorites of the item |
| `CPU` | `String` | **Required**. CPU|
| `camera` | `List<String>` | **Required**. Camera of the item|
| `capacity` | `List<String>` | **Required**. capacity of the item|
| `color` | `List<String>` | **Required**. Color of the item|
| `sd` | `String` | **Required**. SD of the item|
| `ram` | `String` | **Required**. RAM of the item| 
| `images` | `List<String>` | **Required**. URL of the item's picture| 

#### Cart screen

```
  GET https://run.mocky.io/v3/53539a72-3c5f-4f30-bbb1-6ca10d42c149
```

- Basket

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `Int` | **Required**. Id of item |
| `images` | `String` | **Required**. URL of the item's picture |
| `price` | `Int` | **Required**. Price of item |
| `title` | `String` | **Required**. Title of item|
| `count` | `Int?` | **Required**. Count of item |


- Cart

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `String` | **Required**. Id of item |
| `delivery` | `String` | **Required**. free or not |
| `total` | `Int` | **Required**. Total price |
| `basket` | `List<Basket>` | **Required**. Items in basket |

## Screenshots

- Main Screen

![Main Screen](https://user-images.githubusercontent.com/49922631/215332952-fe5d2db6-86d4-478a-8fe1-b0afc6420df7.png)

- Detail Screen

![Detail Screen](https://user-images.githubusercontent.com/49922631/215332999-efc85cf2-68fd-482b-9bae-72717b60f188.png)

- Cart Screen

![Cart Screen](https://user-images.githubusercontent.com/49922631/215333150-13404c09-1eb8-4f26-8757-b730931df64d.png)

- State Network Connection Screen

![State Network Connection Screen](https://user-images.githubusercontent.com/49922631/215512137-be4d8650-bfde-4b90-9cab-28965a27e2fa.jpg)

- Shimmer effect

![Shimmer effect](https://user-images.githubusercontent.com/49922631/215333399-596129c4-4d42-4a42-81a0-0d8434d6b334.png)




## Demo

- Application work displays

![Application work displays](https://user-images.githubusercontent.com/49922631/215334213-01020a41-ead2-485a-b64e-cd0ff9c6b52c.gif)

- Shimmer

![Shimmer](https://user-images.githubusercontent.com/49922631/215333776-d4fd7d2b-34c8-4285-8416-2f75ce4498a8.gif)


## License

[MIT](https://choosealicense.com/licenses/mit/)

