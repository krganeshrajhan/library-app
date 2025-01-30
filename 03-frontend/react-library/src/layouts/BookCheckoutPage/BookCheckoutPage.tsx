import { useEffect, useState } from "react";
import BookModel from "../../models/BookModel";
import { SpinnerLoading } from "../Utils/SpinnerLoading";

export const BookCheckoutPage = () => {

    const [book, setBook] = useState<BookModel>();
    const [isLoadingBook, setIsLoadingBook] = useState(true);
    const [httpError, setHttpError] = useState(null);


    const bookId = (window.location.pathname).split('/')[2];

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

    if (isLoadingBook) {
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
                        </div>
                    </div>
                </div>
                <hr />
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
                    </div>
                </div>
            </div>
        </div>
    );
}