<%@ page import="hotel.rating.HotelRating" %>



<div class="fieldcontain ${hasErrors(bean: hotelRatingInstance, field: 'rating', 'error')} required">
	<label for="rating">
		<g:message code="hotelRating.rating.label" default="Rating" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="rating" type="number" min="1" max="10" value="${hotelRatingInstance.rating}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: hotelRatingInstance, field: 'comment', 'error')} ">
	<label for="comment">
		<g:message code="hotelRating.comment.label" default="Comment" />
		
	</label>
	<g:textField name="comment" value="${hotelRatingInstance?.comment}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hotelRatingInstance, field: 'ratedHotelUri', 'error')} ">
	<label for="ratedHotelUri">
		<g:message code="hotelRating.ratedHotelUri.label" default="Rated Hotel Uri" />
		
	</label>
	<g:textField name="ratedHotelUri" value="${hotelRatingInstance?.ratedHotelUri}"/>
</div>

