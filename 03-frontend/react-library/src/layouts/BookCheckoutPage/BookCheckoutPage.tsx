import { useEffect, useState } from "react";
import BookModel from "../../models/BookModel";
import { SpinnerLoading } from "../Utils/SpinnerLoading";
import { StarsReview } from "../Utils/StarsReview";
import { CheckoutAndReviewBox } from "./CheckoutAndReviewBox";
import ReviewModel from "../../models/ReviewModel";
import { LatestReviews } from "./LatestReviews";
import { useOktaAuth } from "@okta/okta-react";

export const BookCheckoutPage = () => {

    const { authState } = useOktaAuth();

    const [book, setBook] = useState<BookModel>();
    const [isLoadingBook, setIsLoadingBook] = useState(true);
    const [httpError, setHttpError] = useState(null);

    const [reviews, setReviews] = useState<ReviewModel[]>([]);
    const [totalStars, setTotalStars] = useState(0);
    const [isLoadingReview, setIsLoadingReview] = useState(true);

    // Loans Count State
    const [currentLoansCount, setCurrentLoansCount] = useState(0);
    const [isLoadingLoansCount, setIsLoadingLoansCount] = useState(true);

    // Is Book Check Out?
    const [isCheckedOut, setIsCheckedOut] = useState(false);
    const [isLoadingBookCheckedOut, setIsLoadingBookCheckedOut] = useState(true);

    const bookId = (window.location.pathname).split('/')[2];

    useEffect(() => {
        const fetchUserCurrentLoansCount = async () => {
            if (authState && authState.isAuthenticated) {
                const url = `http://localhost:8081/api/books/secure/currentloans/count`;
                const requestOptions = {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${authState.accessToken?.accessToken}`
                }
            };
            const currentLoansCountResponse = await fetch(url, requestOptions);
            if (!currentLoansCountResponse.ok) {
                throw new Error('Something went wrong!');
            }
            const currentLoansCountJson = await currentLoansCountResponse.json();
            setCurrentLoansCount(currentLoansCountJson);
        }
        setIsLoadingLoansCount(false);
    }

    fetchUserCurrentLoansCount().catch((error: any) => {
        setIsLoadingLoansCount(false);
        setHttpError(error.message);
    })
    }, [authState]);

    useEffect(() => {
        const fetchUserCheckedOutBook = async () => {

        }
        fetchUserCheckedOutBook().catch((error: any) => {
            setIsLoadingBookCheckedOut(false);
            setHttpError(error.message);
        })
    }, [authState])

    useEffect(() => {
        const fetchBook = async () => {
            const baseUrl: string = `http://localhost:8081/api/books/${bookId}`;

            const response = await fetch(baseUrl);

            if (!response.ok) {
                throw new Error('Something went wrong!');
            }

            const responseJson = await response.json();

            const responseData = responseJson;

            const loadedBook: BookModel = {
                id: responseData.id,
                title: responseData.title,
                author: responseData.author,
                description: responseData.description,
                copies: responseData.copies,
                copiesAvailable: responseData.copiesAvailable,
                category: responseData.category,
                img: responseData.img
            }

            setBook(loadedBook);
            setIsLoadingBook(false);
        };
        fetchBook().catch((error: any) => {
            setIsLoadingBook(false);
            setHttpError(error.message);
        })
    }, [bookId]);

    useEffect(() => {
        const fetchReviews = async () => {
            const reviewUrl: string = `http://localhost:8081/api/reviews/search/findByBookId?bookId=${bookId}`;
    
            const response = await fetch(reviewUrl);
    
            if (!response.ok) {
                throw new Error('Something went wrong!');
            }
    
            const responseJson = await response.json();
            const responseData = responseJson._embedded.reviews;
    
            const loadedReviews: ReviewModel[] = [];

            let weightedStarReviews = 0;
    
            for (const key in responseData) {
                loadedReviews.push({
                    id: responseData[key].id,
                    userEmail: responseData[key].userEmail,
                    date: responseData[key].date,
                    rating: responseData[key].rating,
                    bookId: responseData[key].bookId,
                    reviewDescription: responseData[key].reviewDescription
                })
                weightedStarReviews += responseData[key].rating;
            }

            if (loadedReviews) {
                const round = (Math.round((weightedStarReviews / loadedReviews.length) *2)/2).toFixed(1);
                setTotalStars(Number(round));
            }
    
            setReviews(loadedReviews);
            setIsLoadingReview(false);
        }
        fetchReviews().catch((error: any) => {
            setIsLoadingReview(false);
            setHttpError(error.message);
        })
    }, [bookId]);



    if (isLoadingBook || isLoadingReview || isLoadingLoansCount) {
        return (
            <SpinnerLoading />
        )
    }

    if (httpError) {
        return (
            <div className="container m-5">
                <p>{httpError}</p>
            </div>
        )
    }

    return(
        <div>
            <div className="container d-none d-lg-block">
                <div className="row mt-5">
                    <div className='col-sm2 col-md-2'>
                        {book?.img ? 
                            <img src={book.img} width='226' height='349' alt="Book" />
                        :
                            <img src={require('./../../Images/BooksImages/book-luv2code-1000.png')} width='226' height='349' alt="Book" />
                        }
                    </div>
                    <div className='col-4 col-md-4 container'>
                        <div className="ml-2">
                            <h2>{book?.title}</h2>
                            <h5 className="text-primary">{book?.author}</h5>
                            <p className="lead">{book?.description}</p>
                            <StarsReview rating={totalStars} size={32} />
                        </div>
                    </div>
                    <CheckoutAndReviewBox book={book} mobile={false} currentLoansCount={currentLoansCount}/>
                </div>
                <hr />
                <LatestReviews reviews={reviews} bookId={book?.id} mobile={false} />
            </div>
            <div className='container d-lg-none mt-5'>
                <div className="d-flex justify-content-center align-items-center">
                    {book?.img ?
                        <img src={book.img} width='226' height='349' alt="Book" />
                    :
                        <img src={require('./../../Images/BooksImages/book-luv2code-1000.png')} width='226' height='349' alt="Book" />
                    }
                </div>
                <div className="mt-4">
                    <div className="ml-2">
                        <h2>{book?.title}</h2>
                        <h5 className="text-primary">{book?.author}</h5>
                        <p className="lead">{book?.description}</p>
                        <StarsReview rating={totalStars} size={32} />
                    </div>
                </div>
                <CheckoutAndReviewBox book={book} mobile={true} currentLoansCount={currentLoansCount} />
                <hr />
                <LatestReviews reviews={reviews} bookId={book?.id} mobile={true} />
            </div>
        </div>
    );
}