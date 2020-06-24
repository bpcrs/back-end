package fpt.capstone.bpcrs.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Review.class)
public abstract class Review_ extends fpt.capstone.bpcrs.component.Auditing_ {

	public static volatile SingularAttribute<Review, Car> car;
	public static volatile SingularAttribute<Review, Integer> rating;
	public static volatile SingularAttribute<Review, String> comment;
	public static volatile SingularAttribute<Review, Account> renter;

	public static final String CAR = "car";
	public static final String RATING = "rating";
	public static final String COMMENT = "comment";
	public static final String RENTER = "renter";

}

