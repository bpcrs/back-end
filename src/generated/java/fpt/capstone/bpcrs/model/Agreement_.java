package fpt.capstone.bpcrs.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Agreement.class)
public abstract class Agreement_ extends fpt.capstone.bpcrs.component.Auditing_ {

	public static volatile SingularAttribute<Agreement, Booking> booking;
	public static volatile SingularAttribute<Agreement, Criteria> criteria;
	public static volatile SingularAttribute<Agreement, Boolean> isApproved;
	public static volatile SingularAttribute<Agreement, String> value;

	public static final String BOOKING = "booking";
	public static final String CRITERIA = "criteria";
	public static final String IS_APPROVED = "isApproved";
	public static final String VALUE = "value";

}

