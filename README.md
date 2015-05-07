# 对象：

### User：

|名称|类型|解释|
|---|---|---|
|u_id|int|用户ID|
|name|String||
|password|String|在获取用户数据的时候不会返回，能不用尽量不要用|
|birthday|String|格式：yyyy-mm-dd|
|gender|String||
|email|String||
|state|int|0代表一般用户，1代表专家用户|
|portrait_url|String|头像url|
|signature|String|个性签名|

### Point:

|名称|类型|解释|
|---|---|---|
|p_id|int|ID|
|lat|double|纬度|
|lng|double|经度|
|name|String|地点名称|
|weight|int|地标权重，在不同缩放程度的地标有不同的权重|

### Spot:

|名称|类型|解释|
|---|---|---|
|s_id|int|ID|
|lat|double|纬度|
|lng|double|经度|
|name|String|景点名称|
|img_url|String|图片链接|
|content|String|景点介绍；**这应该是我们以后必定要修改的地方，不能只是用一个textview来简单摆放文字信息，而是应该更加人性化，交互更详细，更“复杂”**|
|tag|ArrayList|标签，在数据传输的时候转换为以符号"@"分割的String|
|audio_url|String|语音介绍的文件路径|
|author_name|String|作者名称|
|author_id|int|作者ID|
|create_time|String|创建时间,格式：yyyy-mm-dd|
|degree|int|所有用户到过总次数|

### Comment：

|名称|类型|解释|
|---|---|---|
|c_id|int|评论的ID|
|s_id|int|Spot ID|
|spot_name|String|景点的名称|
|u_id|int|用户ID|
|user_url|String|用户头像链接|
|user_name|String|用户名称|
|text|String|评论内容|
|time|String|格式：yyyy-MM-dd hh-mm|
|praise|int|赞的数量|

-------------------------------------------------------------

# 页面总设计：

### 1、登陆注册

### 2、主体页面

参照足迹，划分4个tab：1、热门地点标签推荐；2、地图展示，显示所有point（根据用户是否到过，切换不同颜色），在地图展示页面提供地图编辑按钮以供专家及用户进行编辑；3、发现页面，提供用户附近地点，提供词条搜索；4、个人页面，

3、三个不直接显示但是会频繁切换到的页面：1、用户信息页面；2、Spot信息页面；3、评论页面

-------------------------------------------------------------
### Spot信息页面：

当进入该页面的时候便上传修改last_time(l/s)


-------------------------------------------------------------

# 前端与服务器的交互

**使用http方式来访问服务器**

**基址`http://localIP:8080/imap/` localIP会根据不同环境进行更换**

## 事件模型

|模型|介绍|URL|
|---|---|---|
|用户|登陆|user/login|
|用户|注册|user/register|
|用户|获取其他用户信息|user/getUser|
|用户|更新信息|user/updateUser|
|Spot|热门Spots获取|spot/getHotSpots|
|Spot|附近Spots获取|spot/getNearSpots|
|Spot|获取创建的Spots列表|spot/getMyCreate|
|Spot|获取编辑过的Spots列表|spot/getMyModify|
|Spot|创建新的Spot|spot/createSpot|
|Spot|编辑已存在的Spot|spot/modifySpot|
|Point|Point列表获取|point/getPointList|
|Comment|某个Spot的最热Comment列表的获取|comment/getSpotHotCmt|
|Comment|某个Spot的最新Comment列表的获取|comment/getSpotNewCmt|
|Comment|某个用户的Comment列表获取|comment/getUserCmt|

## 错误：

|错误类型|错误描述|
|---|---|
|0|操作成功|
|101|用户名不存在|
|102|密码错误|
|103|注册时用户名已存在|
|201|提交请求数据格式错误，无法解析|
|202|发送请求时缺少必要的数据|
|301|用户无权限获取或者修改某些数据|
|302|用户未登陆因此没有获取某些数据的权限|
|999|其他类型的失败|

**If you have encounter some error that doesn't appear in the above table and it is not necessary to identify, you can use 999 as recall data.**

**If you need some specific code to identify specific error, Please bring up the requirement.**

## 事件详细描述：

#### 1、登陆

#### 提交

|名称|类型|解释|
|---|---|---|
|name|String|
|password|String|

#### 返回

|名称|类型|解释|
|---|---|---|
|error_type|String||
|error_message|String||
|user|jsonObject|**@{USER}**|

示例：
	
	{
		"error_type" : "0",
		"error_message" : "操作成功",
		"user" :　
			"｛
				"u_id" :　"1",
				"name" : "livhong",
				"birthday" : "2015-04-27",
				"gender" : "male",
				"email" :　"807049189@qq.com",
				"state" : "0", //0代表普通用户;
				"portrait" : "http://192.168.1.1:8080/imap/img/livhong.png",
				"signature" : "I am test text; I am test text;"
			}"
	}


#### 2、注册

#### 提交

|名称|类型|解释|
|---|---|---|
|name|String|
|password|String||
|email|String||
|birthday|String|
|gender|String|

#### 返回

同`登录`

#### 3、更新用户信息

#### 提交

**@{USER}**

**若有头像的更新在用户跟新过头像后在提交@{USER}之前先单独上传头像图片文件，在上传过文件之后会以json格式返回三个值，error_type、error_message和img_url，之后更新的用户信息中的img_url则使用该url。**


#### 返回

同`登录`

#### 4、获取其他用户信息

#### 提交

|名称|类型|解释|
|---|---|---|
|u_id|String|所要查看的用户的ID|

#### 返回

同`登录`

#### 5、热门spot的获取

**图片和音频同头像，提交在spot的json数据前。**

#### 提交

|名称|类型|解释|
|---|---|---|
|start_point|String|获取信息的开始位置|
|limit|String|每次从服务器返回spot的数量|

#### 返回

|名称|类型|解释|
|---|---|---|
|error_type|String||
|error_message|String|
|number|String|返回的spot列表中spot的数量|
|has_more|String|值为"YES"或"NO"，解释数据库中是否还有更多的数据可以读|
|spots|jsonArray|@{SPOT} json list|

示例

	{
		"error_type" : "0",
		"error_message" : "操作成功",
		"number" :　"10",
		"has_more" : "NO",
		"spots" : {
				{
					"s_id" : "1",
					"lat" :　"30.00000",
					"lng" :　"120.00000",
					"name" : "GuangHua Building",
					"visted_time" : "5",
					"last_timel" : "201934898920",
					"last_times" : "2015-04-27 00-51",
					"img_url" : "http://192.168.1.1:8080/imap/image/guanhua.png",
					"content" : "这是复旦大学最高端的建筑。", 
					"tag" : "复旦大学@五角场",
					"audio_url" :　"http://192.168.1.1:8080/imap/audio/guanghua.mp3",
					"author_name" : "livhong",
					"author_id" : "1",
					"create_time" : "2015-04-27",
					"degree" :　"23"
				}
			}
	}

#### 6、附近Spots获取

#### 提交

|名称|类型|解释|
|---|---|---|
|start_point|String|开始偏移|
|limit|String|返回jsonlist的长度|
|lat|String|纬度|
|lng|String|经度|

#### 返回

同`获取热门Spots`

#### 7、获取创建的Spots列表

#### 提交

|名称|类型|解释|
|---|---|---|
|start_point|String|开始偏移|
|limit|String|返回jsonlist的长度|
|u_id|String|用户ID|

#### 返回

同`获取热门Spots`

#### 8、获取编辑过的Spots列表

#### 提交

|名称|类型|解释|
|---|---|---|
|start_point|String|开始偏移|
|limit|String|返回jsonlist的长度|
|u_id|String|用户ID|

#### 返回

同`获取热门Spots`

#### 9、创建新的Spot

#### 提交

**@{SPOT}**

**提交之前先传图片和音频文件**

#### 返回

|名称|类型|解释|
|---|---|---|
|error_type|String|
|error_message|String|

#### 10、编辑已存在的Spot

#### 提交

|名称|类型|解释|
|---|---|---|
|u_name|String|修改者名称|
|u_id|String|修改者ID|
|time|String|修改时间，格式：yyyy-mm-dd|
|spot|jsonObject|@{SPOT}|

#### 返回

|名称|类型|解释|
|---|---|---|
|error_type|String|
|error_message|String|

#### 11、Point列表获取

#### 提交

|名称|类型|解释|
|---|---|---|
|weight|String|需要显示的point的权重|
|lat|String|用户所看的地图的中间位置的纬度|
|lng|String|用户所看到的地图的中间位置的经度|

#### 返回

|名称|类型|解释|
|---|---|---|
|error_type|String|
|error_message|String|
|number|String|返回points的数量|
|points|jsonarray|@{POINT}json list|

#### 12、某个Spot的最热Comment列表的获取

#### 提交

|名称|类型|解释|
|---|---|---|
|s_id|String|spot的ID|
|start_point|String|起始位置|
|limit|String|每次获得数据的数量|

#### 返回

|名称|类型|解释|
|---|---|---|
|error_type|String||
|error_message|String||
|number|String|返回comment的数量|
|has_more|String|YES or NO|
|comments|jsonArray|@{COMMENT}json list|

#### 13、某个Spot的最新Comment列表的获取

同`某个Spot的最热Comment列表的获取`

#### 14、某个用户的Comment列表获取

#### 提交

|名称|类型|解释|
|---|---|---|
|u_id|String|user的ID|
|start_point|String|起始位置|
|limit|String|每次获得数据的数量|

#### 返回

同`某个Spot的最热Comment列表的获取`