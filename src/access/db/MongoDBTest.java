package access.db;

import access.util.BeanUtil;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDBTest {

    public static void main(String args[]) {
        try {
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            // 连接到数据库
            DB db = mongoClient.getDB("Access");
            DBCollection users = db.getCollection("user");

/*            //Query
            DBObject query = new BasicDBObject("sex", "female");
            DBCursor cursor = users.find(query).sort(new BasicDBObject("uid", 1));
            int i = 1;
            while (cursor.hasNext()) {
                System.out.println("Inserted Document: " + i);
                DBObject u = cursor.next();
                System.out.println(u);
                Map map = u.toMap();
                System.out.println(map.toString());
                i++;
            }*/

/*            //Update
            DBObject q = new BasicDBObject("uid", 9);
            DBObject set = new BasicDBObject("$set", new BasicDBObject("sex", "female"));
            users.update(q, set, true, false);*/

/*            //Insert
            DBObject user = new BasicDBObject();
            user.put("uid", 8);
            user.put("name", "maoxy");
            user.put("age",25);
            System.out.println(users.save(user).getN());*/

/*            //JSON<——>DBObject
            JSONObject user = new JSONObject();
            //user = JSONObject.fromObject(users.findOne(1));
            user.put("uid", 8);
            user.put("name", "lanjw");
            user.put("age", 26);
            user.put("sex", "female");
            System.out.println(user);

            DBObject newUser = (DBObject) com.mongodb.util.JSON.parse(user.toString());
            users.save(newUser);*/

            //User<——>DBObject
            BeanUtil beanUtil = new BeanUtil();
            DBObject dbuser = users.findOne(9);
            System.out.println(dbuser);

//            JSONObject juser;
//            juser = JSONObject.fromObject(dbuser);
//            System.out.println(juser);
//            System.out.println(dbuser.toMap());
//            System.out.println(dbuser.toString());

            User user = beanUtil.dbObject2Bean(dbuser, new User());
            System.out.println("uid=" + user.get_id());
            System.out.println("name=" + user.getName());
            System.out.println("age=" + user.getAge());
            System.out.println("sex=" + user.getSex());

            User user2 = new User(10,"weibl","female",24);
            DBObject dbuser2 = beanUtil.bean2DBObject(user2);
            System.out.println("dbuser2="+dbuser2);
            users.save(dbuser2);

            mongoClient.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static class User {
        private int _id;
        private String name;
        private String sex;
        private int age;

        public User() {
        }

        public User(int _id, String name, String sex, int age) {
            this._id = _id;
            this.name = name;
            this.sex = sex;
            this.age = age;
        }

        public int get_id() {
            return _id;
        }

        public void set_id(int _id) {
            this._id = _id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
