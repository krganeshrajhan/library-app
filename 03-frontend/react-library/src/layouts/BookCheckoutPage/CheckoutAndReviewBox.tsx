import { Link } from "react-router-dom";
import BookModel from "../../models/BookModel";
import { useOktaAuth } from "@okta/okta-react";

export const CheckoutAndReviewBox: React.FC<{ book: BookModel | undefined, mobile: boolean,
    currentLoansCount: number }> = (props) => {

    const book = props.book;
    const mobile = props.mobile;
    const { authState } = useOktaAuth();

    return (
        <div className={mobile ? 'card d-flex mt-5' : 'card col-3 container d-flex mb-5'}>
            <div className="card-body container">
                <div className="mt-3">
                    <p>
                        <b>{props.currentLoansCount}/5 </b>
                        books checked out
                    </p>
                    <hr />
                    {props.book && props.book.copiesAvailable && props.book.copiesAvailable > 0 ?
                        <h4 className="text-success">
                            Available
                        </h4>
                        : (
                            <h4 className="text-danger">
                                Wait List
                            </h4>
                        )}
                    <div className="row">
                        <p className="col-6 lead">
                            <b>{props.book?.copies} </b>
                            copies
                        </p>
                        <p className="col-6 lead">
                            <b>{props.book?.copiesAvailable} </b>
                            available
                        </p>
                    </div>
                </div>
                {!authState?.isAuthenticated ?
                    <Link to='/#' className="btn btn-success btn-lg">Sign in</Link>
                    :
                    <Link to={`/checkout/${props.book?.id}`} className="btn btn-success btn-lg">Checkout</Link>
                }
                    <hr />
                    <p className="mt-3">
                        This number can change until placing order has been complete.
                    </p>
                    {!authState?.isAuthenticated ?
                    <p>
                        Sign in to be able to leave a review.
                    </p>
                    :
                    <></>
                }
            </div>
        </div>
    )
}
