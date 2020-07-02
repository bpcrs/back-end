package fpt.capstone.bpcrs.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Booking.class)
public abstract class Booking_ extends fpt.capstone.bpcrs.component.Auditing_ {

	public static volatile SingularAttribute<Booking, Date> from_date;
	public static volatile SingularAttribute<Booking, Date> to_date;
	public static volatile SingularAttribute<Booking, Car> car;
	public static volatile SingularAttribute<Booking, Integer> fixingPrice;
	public static volatile SingularAttribute<Booking, String> destination;
	public static volatile SingularAttribute<Booking, String> description;
	public static volatile ListAttribute<Booking, Agreement> agreements;
	public static volatile SingularAttribute<Booking, Integer> rentPrice;
	public static volatile SingularAttribute<Booking, Account> lessor;
	public static volatile SingularAttribute<Booking, Account> renter;
	public static volatile SingularAttribute<Booking, String> status;

	public static final String FROM_DATE = "from_date";
	public static final String TO_DATE = "to_date";
	public static final String CAR = "car";
	public static final String FIXING_PRICE = "fixingPrice";
	public static final String DESTINATION = "destination";
	public static final String DESCRIPTION = "description";
	public static final String AGREEMENTS = "agreements";
	public static final String RENT_PRICE = "rentPrice";
	public static final String LESSOR = "lessor";
	public static final String RENTER = "renter";
	public static final String STATUS = "status";

}

