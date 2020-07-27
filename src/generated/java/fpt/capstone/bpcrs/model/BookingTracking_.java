package fpt.capstone.bpcrs.model;

import fpt.capstone.bpcrs.constant.BookingEnum;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BookingTracking.class)
public abstract class BookingTracking_ extends fpt.capstone.bpcrs.component.Auditing_ {

	public static volatile SingularAttribute<BookingTracking, Booking> booking;
	public static volatile SingularAttribute<BookingTracking, BookingEnum> status;

	public static final String BOOKING = "booking";
	public static final String STATUS = "status";

}

