package fpt.capstone.bpcrs.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Car.class)
public abstract class Car_ extends fpt.capstone.bpcrs.component.Auditing_ {

	public static volatile SingularAttribute<Car, Account> owner;
	public static volatile SingularAttribute<Car, Boolean> isAvailable;
	public static volatile ListAttribute<Car, Image> images;
	public static volatile SingularAttribute<Car, String> year;
	public static volatile SingularAttribute<Car, String> sound;
	public static volatile SingularAttribute<Car, String> screen;
	public static volatile SingularAttribute<Car, String> plateNum;
	public static volatile SingularAttribute<Car, Boolean> autoDriver;
	public static volatile SingularAttribute<Car, Integer> seat;
	public static volatile ListAttribute<Car, Booking> car;
	public static volatile SingularAttribute<Car, Double> price;
	public static volatile SingularAttribute<Car, String> name;
	public static volatile SingularAttribute<Car, String> VIN;
	public static volatile SingularAttribute<Car, Model> model;
	public static volatile SingularAttribute<Car, Brand> brand;
	public static volatile SingularAttribute<Car, String> status;

	public static final String OWNER = "owner";
	public static final String IS_AVAILABLE = "isAvailable";
	public static final String IMAGES = "images";
	public static final String YEAR = "year";
	public static final String SOUND = "sound";
	public static final String SCREEN = "screen";
	public static final String PLATE_NUM = "plateNum";
	public static final String AUTO_DRIVER = "autoDriver";
	public static final String SEAT = "seat";
	public static final String CAR = "car";
	public static final String PRICE = "price";
	public static final String NAME = "name";
	public static final String V_IN = "VIN";
	public static final String MODEL = "model";
	public static final String BRAND = "brand";
	public static final String STATUS = "status";

}

