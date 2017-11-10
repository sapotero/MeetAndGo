package sapotero.meetandgo.db;


import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;

@Entity
abstract class User {

  @Key
  @Generated
  int id;

  String email;
  String password;
  String info;
  String phone;
  String url;
}
