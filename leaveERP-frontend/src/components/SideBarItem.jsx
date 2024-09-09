import { useLocation, useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAddressCard, faArrowAltCircleDown, faCalendar, faNetworkWired } from '@fortawesome/free-solid-svg-icons';
import { faUserTie } from '@fortawesome/free-solid-svg-icons';

const text_color = "text-gray-200";
const bg_color = "bg-slate-800";
const hover_color = "hover:bg-purple-400";
const active_color = "bg-purple-700";

class SVG {

    static employees = (
        <FontAwesomeIcon icon={faUserTie} />
    );

    static leaves = (
        <FontAwesomeIcon icon={faCalendar} />
    );

    static departments = (
        <FontAwesomeIcon icon={faNetworkWired} />
    );
}

const SidebarItem = ({ path, handleActiveTab, children }) => {
    
    const location = useLocation();
    const navigate = useNavigate();

    return (
        <li onClick={() => { navigate(path); handleActiveTab(path); }}>
            <a
                className={`flex items-center p-2 
                ${text_color} rounded-lg dark:text-white 
                ${location.pathname === path ? active_color : ''}
                ${hover_color} dark:hover:bg-gray-700 group 
                `}
            >

                {SVG[path.slice(1)]}
                {children}
            </a>
        </li>
    );
}

export default SidebarItem;